package com.zenika.dorm.maven.processor.extension;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.helper.MavenExtensionHelper;
import com.zenika.dorm.maven.model.MavenConstant;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.model.builder.MavenMetadataUriBuilder;
import com.zenika.dorm.maven.service.MavenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven webservice push request : " + request);
        }

        if (!request.hasFile()) {
            throw new MavenException("File is required");
        }

        DormWebServiceResult.Builder responseBuilder = new DormWebServiceResult.Builder(
                MavenMetadata.EXTENSION_NAME);

        MavenUri mavenUri = new MavenUri(request.getProperty("uri"));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven uri : " + mavenUri);
        }

        // ignore put's of maven-medata.xml file
        if (StringUtils.equals(mavenUri.getFilename().getFilename(), MavenConstant.Special.MAVEN_METADATA_XML)) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Ignore " + MavenConstant.Special.MAVEN_METADATA_XML);
            }

            return responseBuilder.succeeded().build();
        }

        MavenMetadata metadata = MavenMetadataUriBuilder.buildMavenMetadata(mavenUri);

        File file = request.getFile();
        String extension = metadata.getExtension();

        // md5 or sha1
        if (MavenExtensionHelper.isHash(extension)) {
            mavenService.storeHash(metadata, file);
        }

        // pom
        else if (StringUtils.equals(metadata.getExtension(), MavenConstant.FileExtension.POM)) {
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

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven webservice get request : " + request);
        }

        MavenUri mavenUri = new MavenUri(request.getProperty("uri"));
        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven uri : " + mavenUri);
        }

        return null;
    }
}