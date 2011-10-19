package com.zenika.dorm.maven.processor.extension;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DerivedObject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.processor.extension.ProcessorExtension;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.FileValidator;
import com.zenika.dorm.maven.constant.MavenConstant;
import com.zenika.dorm.maven.model.MavenPlugin;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.model.PomObject;
import com.zenika.dorm.maven.service.MavenProxyService;
import com.zenika.dorm.maven.service.MavenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The maven processor needs to create an abstract dependency node which will be the parent of the
 * following maven nodes : maven pom node, maven jar node, maven sha1 node, etc...
 * The only difference between theses nodes is the file and his type : pom.xml, jar, etc...
 * <p/>
 * See : https://docs.google.com/drawings/d/1N1epmWY3dUy7th-VwrSNk1HXf6srEi0RoUoETQbe8qM/edit?hl=fr
 * <p/>
 * snapshot upload -
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenProcessor extends ProcessorExtension {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProcessor.class);

    @Inject
    private MavenService mavenService;

    @Inject
    private DormService dormService;

    @Inject
    private MavenProxyService proxyService;

    @Inject
    private FileValidator fileValidator;

    /**
     * Process:
     * 1. Try to get maven artifact corresponding to the url identifier
     * 2. If yes, override file only
     * 3. If no, add file
     * 4. In both case check if maven deploy is complete. It means :
     * - presence of
     * -- jar
     * -- jar.md5
     * -- jar.sha1
     * -- pom
     * -- pom.md5
     * -- pom.sha1
     * -- maven-metadata.xml
     *
     * @param uri
     * @param file
     * @return
     */
    @Override
    public DormWebServiceResult push(String uri, File file) {
        MavenUri mavenUri = new MavenUri(checkNotNull(uri));

        // ignore put of maven-metadata.xml* files
        if (!mavenUri.isMavenMetadataUri()) {
            LOG.info("Ignore {}", mavenUri);
            return getSucceedResponse();
        }
        DormMetadata dormMetadata = storeDormMetadata(mavenUri);
        MavenPlugin mavenPlugin = (MavenPlugin) dormMetadata.getPlugin(MavenPlugin.MAVEN_PLUGIN);

        if (mavenUri.getFilename().isPomFile()) {
            storePomObject(file, mavenUri, dormMetadata, mavenPlugin);
        } else if (mavenUri.getFilename().isJarFile()) {
            storeDerivedObject(file, mavenUri, dormMetadata);
        }
        return getSucceedResponse();
    }


    private DormMetadata storeDormMetadata(MavenUri mavenUri) {
        DormMetadata dormMetadata = new DormMetadata();
        if (dormService.isDormMetadataAlreadyExist(dormMetadata)) {
            if (dormMetadata.hasPlugin(MavenPlugin.MAVEN_PLUGIN)) {
                return dormMetadata;
            } else {
                MavenPlugin mavenPlugin = new MavenPlugin();
                mavenPlugin.setArtifactId(mavenUri.getArtifactId());
                mavenPlugin.setGroupId(mavenUri.getGroupId());
                mavenPlugin.setVersion(mavenUri.getVersion());
                dormMetadata.addPluginMetadata(mavenPlugin);
                return dormService.updateDormMetadata(dormMetadata);
            }
        } else {
            return dormService.createDormMetadata(dormMetadata);
        }
    }

    private PomObject getPomObject(MavenPlugin mavenPlugin) {
        if (mavenPlugin.hasPomObject()) {
            return mavenPlugin.getPomObject();
        } else {
            return new PomObject();
        }
    }

    private DerivedObject getDerivedObject(DormMetadata dormMetadata) {
        if (dormMetadata.hasDerivedObject()) {
            return dormMetadata.getDerivedObject();
        } else {
            return new DerivedObject();
        }
    }

    private void storePomObject(File file, MavenUri mavenUri, DormMetadata dormMetadata, MavenPlugin mavenPlugin) {
        PomObject pomObject = getPomObject(mavenPlugin);
        if (mavenUri.getFilename().isHashMd5()) {
            pomObject.setHashMd5(getHashStoredInFile(file));
        } else if (mavenUri.getFilename().isHashSha1()) {
            pomObject.setHashSha1(getHashStoredInFile(file));
        } else {
            pomObject.setLocation(generateLocation(mavenUri));
            dormService.storeDerivedObject(pomObject, file);
        }
        mavenPlugin.setPomObject(pomObject);
        dormService.updateDormMetadata(dormMetadata);
    }

    private void storeDerivedObject(File file, MavenUri mavenUri, DormMetadata dormMetadata) {
        DerivedObject derivedObject = getDerivedObject(dormMetadata);
        if (mavenUri.getFilename().isHashMd5()) {
            derivedObject.setHashMd5(getHashStoredInFile(file));
        } else if (mavenUri.getFilename().isHashSha1()) {
            derivedObject.setHashSha1(getHashStoredInFile(file));
        } else {
            derivedObject.setLocation(generateLocation(mavenUri));
            dormService.storeDerivedObject(derivedObject, file);
        }
        dormMetadata.setDerivedObject(derivedObject);
        dormService.updateDormMetadata(dormMetadata);
    }

    private String getHashStoredInFile(File file) {
        try {
            Scanner reader = new Scanner(file);
            return reader.nextLine();
        } catch (FileNotFoundException e) {
            throw new CoreException("Unable to read this file: " + file, e);
        }

    }

    private String generateLocation(MavenUri mavenUri) {
        return mavenUri.getUri();
    }

    @Override
    public DormWebServiceResult get(DormWebServiceRequest request) {
        DormWebServiceResult.Builder responseBuilder = createResponseBuilder();
        MavenUri mavenUri = new MavenUri(request.getProperty("uri"));
        if (!mavenUri.isMavenMetadataUri()) {
            return responseBuilder.notfound().build();
        }
        MavenPlugin mavenMetadata = createMavenMetadata(mavenUri);
        DormResource dormResource = mavenService.getArtifact(mavenMetadata);
        if (mavenService.isUseProxy(dormResource)) {
            return getResponseWithProxy(responseBuilder, dormResource, mavenMetadata);
        }
        return responseBuilder.file(dormResource.getFile()).succeeded().build();
    }

    private DormMetadata createMavenMetadata(MavenUri mavenUri) {
        DormMetadata dormMetadata = new DormMetadata();
        dormMetadata.setName(mavenUri.getArtifactId());
        dormMetadata.setVersion(mavenUri.getVersion());
        return dormMetadata;
    }

    private DormWebServiceResult getSucceedResponse() {
        return new DormWebServiceResult.Builder()
                .origin(MavenPlugin.MAVEN_PLUGIN)
                .succeeded()
                .build();
    }

    private DormWebServiceResult getResponseWithProxy(DormWebServiceResult.Builder responseBuilder, DormResource dormResource, MavenPlugin mavenMetadata) {
        dormResource = proxyService.getArtifact(mavenMetadata);
        if (isNotAvailableResourceFromProxy(dormResource)) {
            return responseBuilder.notfound().build();
        } else {
            if (dormResource.hasFile()) {
                return responseBuilder.file(dormResource.getFile()).succeeded().build();
            } else if (dormResource.hasInputStream()) {
                return responseBuilder.inputStream(dormResource.getInputStream()).succeeded().build();
            } else {
                return responseBuilder.notfound().build();
            }
        }
    }

    private boolean isNotAvailableResourceFromProxy(DormResource dormResource) {
        return dormResource == null;
    }

    @Override
    public DormWebServiceResult pushFromGenericRequest(DormWebServiceRequest request) {
        return push(getRequestFromGeneric(request));
    }

    @Override
    public DormWebServiceResult getFromGenericRequest(DormWebServiceRequest request) {
        return get(getRequestFromGeneric(request));
    }

    private DormWebServiceRequest getRequestFromGeneric(DormWebServiceRequest request) {
        return new DormWebServiceRequest.Builder(request)
                .property("uri", request.getProperty("path"))
                .build();
    }
}