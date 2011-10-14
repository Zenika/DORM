package com.zenika.dorm.maven.processor.extension;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.processor.extension.ProcessorExtension;
import com.zenika.dorm.core.service.FileValidator;
import com.zenika.dorm.maven.constant.MavenConstant;
import com.zenika.dorm.maven.helper.MavenExtensionHelper;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.service.MavenProxyService;
import com.zenika.dorm.maven.service.MavenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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
     * @param request
     * @return
     */
    @Override
    public DormWebServiceResult push(DormWebServiceRequest request) {

        checkNotNull(request);
        fileValidator.validateFile(request.getFile());
        LOG.debug("Maven webservice push request : {}", request);


        DormWebServiceResult.Builder responseBuilder = new DormWebServiceResult.Builder()
                .origin(MavenMetadata.EXTENSION_NAME);

        String uri = checkNotNull(request.getProperty("uri"));

        // ignore put's of maven-medata.xml* files
        if (StringUtils.endsWithAny(uri,
                MavenConstant.Special.MAVEN_METADATA_XML, MavenConstant.Special.MAVEN_METADATA_XML + ".md5",
                MavenConstant.Special.MAVEN_METADATA_XML + ".sha1")) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Ignore " + uri);
            }

            return responseBuilder.succeeded().build();
        }

        MavenUri mavenUri = new MavenUri(uri);
        LOG.debug("Maven uri : {}", mavenUri);


        MavenMetadata metadata = mavenService.buildMavenMetadata(mavenUri);

        File file = request.getFile();
        String extension = metadata.getBuildInfo().getExtension();

        // md5 or sha1
        if (MavenExtensionHelper.isHash(extension)) {
            mavenService.storeHash(metadata, file);
        }

        // pom
        else if (StringUtils.equals(metadata.getBuildInfo().getExtension(), MavenConstant.Extension.POM)) {
            mavenService.storePom(metadata, file);
        }

        // artifact's binary
        else {
            mavenService.storeMetadataWithArtifact(metadata, file);
        }

        return responseBuilder.succeeded().build();
    }


    @Override
    public DormWebServiceResult get(DormWebServiceRequest request) {

        DormWebServiceResult.Builder responseBuilder = createBuilder();

        MavenMetadata mavenMetadata = createMavenMetadata(request);
        if (mavenMetadata.isMavenMetadata()) {
            return responseBuilder.notfound().build();
        }

        DormResource dormResource = mavenService.getArtifact(mavenMetadata);
        if (mavenService.isUseProxy(dormResource)) {
            return getResponseWithProxy(responseBuilder, dormResource, mavenMetadata);
        }
        return responseBuilder.file(dormResource.getFile()).succeeded().build();
    }

    private MavenMetadata createMavenMetadata(DormWebServiceRequest request) {
        MavenUri mavenUri = new MavenUri(request.getProperty("uri"));
        return mavenService.buildMavenMetadata(mavenUri);
    }

    private DormWebServiceResult.Builder createBuilder() {
        return new DormWebServiceResult.Builder()
                .origin(MavenMetadata.EXTENSION_NAME);
    }

    private boolean isMavenMetadata(DormWebServiceRequest request) {
        MavenUri mavenUri = new MavenUri(request.getProperty("uri"));
        return StringUtils.equals(mavenUri.getFilename().getFilename(), MavenConstant.Special.MAVEN_METADATA_XML);
    }

    private DormWebServiceResult getResponseWithProxy(DormWebServiceResult.Builder responseBuilder, DormResource dormResource, MavenMetadata mavenMetadata) {
        dormResource = proxyService.getArtifact(mavenMetadata);
        if (isNotAvailableResourceFromProxy(dormResource)) {
            return responseBuilder.notfound().build();
        }
        //TODO Antoine
        return null;
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