package com.zenika.dorm.maven.processor.extension;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.builder.DependencyBuilderFromRequest;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.model.impl.Usage;
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
    public DormServiceGetRequest buildGetRequest(DormRequest request) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven get with request : " + request);
        }

        MavenMetadataExtension mavenMetadata = new MavenMetadataExtensionBuilder(request).build();

        DormServiceGetRequest getRequest;

        if (mavenMetadata.isMavenMetadata()) {
            getRequest = new MavenServiceGetRequestBuilder(PROCESS_GET_METADATAXML_FILE, mavenMetadata)
                    .withArtifactId()
                    .withGroupId()
                    .withVersion()
                    .repositoryRequest(false)
                    .build();
        } else {
            getRequest = new MavenServiceGetRequestBuilder(PROCESS_GET_ARTIFACT, mavenMetadata)
                    .withAll()
                    .repositoryRequest(true)
                    .build();
        }


//        if (LOG.isDebugEnabled()) {
//            LOG.debug("Maven metadata extension from request : " + mavenMetadata);
//        }

//        DormMetadata metadata = new MetadataBuilderFromRequest(type, request, mavenMetadata).build();

//        if (LOG.isDebugEnabled()) {
//            LOG.debug("Maven metadata from request : " + metadata);
//        }

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

        File mavenMetadataFile = new File("tmp/maven-tmp/maven-metadata.xml");
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
    public DormServicePutRequest buildPutRequest(DormRequest request) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven push with request : " + request);
        }

        if (!request.hasFile()) {
            throw new MavenException("File is required.");
        }

        MavenMetadataExtension extension = new MavenMetadataExtensionBuilder(request).build();

        String type = extension.getExtension();

        // entity dependencuy has no file
        DormRequest entityRequest = new DormRequestBuilder(request)
                .file(null)
                .filename(null)
                .build();

        Dependency entityDependency = new DependencyBuilderFromRequest(entityRequest, ENTITY_TYPE,
                extension).build();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven entity dependency = " + entityDependency);
        }

        // replace the default usage by the maven internal for the child dependency
        Usage childUsage = Usage.createInternal(MavenMetadataExtension.EXTENSION_NAME);

        Dependency dependency = new DependencyBuilderFromRequest(request, type, extension)
                .usage(childUsage)
                .build();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven real dependency = " + dependency);
        }

        DependencyNode root = DefaultDependencyNode.create(entityDependency);
        DependencyNode node = DefaultDependencyNode.create(dependency);
        root.addChild(node);

        DefaultDormServicePutRequest putRequest = new DefaultDormServicePutRequest(PROCESS_PUT_ARTIFACT);
        putRequest.setNode(root);

        return putRequest;
    }
}