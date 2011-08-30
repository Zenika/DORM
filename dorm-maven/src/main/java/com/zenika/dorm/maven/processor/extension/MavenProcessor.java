package com.zenika.dorm.maven.processor.extension;

import com.zenika.dorm.core.exception.DormProcessException;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormWebServiceRequest;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.builder.DependencyBuilderFromRequest;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.impl.put.DefaultDormServicePutRequest;
import com.zenika.dorm.core.service.put.DormServicePutRequest;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenConstant;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtensionBuilder;
import com.zenika.dorm.maven.processor.comparator.MavenSnapshotTimestampComparator;
import com.zenika.dorm.maven.processor.helper.MavenProcessorHelper;
import com.zenika.dorm.maven.service.get.MavenServiceGetRequestBuilder;
import com.zenika.dorm.maven.writer.MavenMetadataFileWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

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
public class MavenProcessor implements ProcessorExtension {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProcessor.class);

    private static final String PROCESS_GET_METADATAXML_FILE = "get_metadataxml_file";
    private static final String PROCESS_GET_ARTIFACT = "get_artifact";
    private static final String PROCESS_PUT_ARTIFACT = "put_artifact";

    public static final String ENTITY_TYPE = "entity";

    @Override
    public DormServiceGetRequest buildGetRequest(DormWebServiceRequest request) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Build maven get request from webservice request : " + request);
        }

        MavenMetadataExtension mavenMetadata = new MavenMetadataExtensionBuilder(request).build();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven metadata : " + mavenMetadata);
        }

        DormServiceGetRequest getRequest;

        if (mavenMetadata.isMavenMetadata()) {
            LOG.trace("Maven metadata is maven-metadata.xml file");
            getRequest = new MavenServiceGetRequestBuilder(PROCESS_GET_METADATAXML_FILE, mavenMetadata)
                    .withArtifactId()
                    .withGroupId()
                    .withVersion()
                    .repositoryRequest(false)
                    .build();
        } else {
            LOG.trace("Maven metadata is representation of an maven artifact");
            getRequest = new MavenServiceGetRequestBuilder(PROCESS_GET_ARTIFACT, mavenMetadata)
                    .withAll()
                    .repositoryRequest(true)
                    .build();
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("Maven get request : " + getRequest);
        }

        return getRequest;
    }

    @Override
    public Dependency buildDependency(DormServiceGetResult result) {

        if (!result.hasResult()) {
            throw new DormProcessException("No result").type(DormProcessException.Type.NULL);
        }

        Dependency dependency;
        if (StringUtils.equals(result.getProcessName(), PROCESS_GET_METADATAXML_FILE)) {
            dependency = buildMavenMetadataFile(result);
        } else if (StringUtils.equals(result.getProcessName(), PROCESS_GET_ARTIFACT)) {
            dependency = result.getUniqueNode().getDependency();
        } else {
            throw new MavenException("Cannot find builder for maven process : " + result.getProcessName());
        }

        return dependency;
    }

    private Dependency buildMavenMetadataFile(DormServiceGetResult result) {

        Collections.sort(result.getNodes(), new Comparator<DependencyNode>() {

            @Override
            public int compare(DependencyNode node1, DependencyNode node2) {

                MavenMetadataExtension extension1 = MavenProcessorHelper.getMavenMetadata(node1);
                MavenMetadataExtension extension2 = MavenProcessorHelper.getMavenMetadata(node2);

                return new MavenSnapshotTimestampComparator().compare(extension1, extension2);
            }
        });

        File folders = new File("tmp/maven-tmp");
        folders.mkdirs();

        File mavenMetadataFile = new File(folders, "maven-metadata.xml");
        MavenMetadataFileWriter writer = new MavenMetadataFileWriter(mavenMetadataFile);

        for (DependencyNode node : result.getNodes()) {
            MavenMetadataExtension metadata = MavenProcessorHelper.getMavenMetadata(node);
            writer.write(metadata);
        }

        DormResource resource = DefaultDormResource.create(MavenConstant.Special.MAVEN_METADATA_XML,
                mavenMetadataFile);

        return DefaultDependency.create(result.getNodes().get(0).getDependency().getMetadata(), resource);
    }

    @Override
    public DormServicePutRequest buildPutRequest(DormWebServiceRequest request) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven push with request : " + request);
        }

        if (!request.hasFile()) {
            throw new MavenException("File is required.");
        }

        MavenMetadataExtension extension = new MavenMetadataExtensionBuilder(request).build();

        String type = extension.getExtension();

        Dependency dependency = new DependencyBuilderFromRequest(request, type, extension)
                .build();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven real dependency = " + dependency);
        }

        DependencyNode node = DefaultDependencyNode.create(dependency);

        DefaultDormServicePutRequest putRequest = new DefaultDormServicePutRequest(PROCESS_PUT_ARTIFACT);
        putRequest.setNode(node);

        return putRequest;
    }
}