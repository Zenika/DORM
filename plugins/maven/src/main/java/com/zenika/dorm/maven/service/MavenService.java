package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenService {

    private static final Logger LOG = LoggerFactory.getLogger(MavenService.class);

    @Inject
    private DormService dormService;

    @Inject
    private MavenHashService hashService;

    public void storeMetadataWithArtifact(MavenMetadata metadata, File file) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven metadata with artifact : " + metadata);
        }

        DormResource resource = DefaultDormResource.create(
                metadata.getName() + "." + metadata.getBuildInfo().getExtension(), file);

        DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
                .override(true);

        dormService.storeMetadata(metadata);
        dormService.storeResource(resource, metadata, config);
    }

    public void storeHash(MavenMetadata metadata, File file) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven hash for metadata : " + metadata);
        }

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
                .override(true);

        dormService.storeResource(resource, metadata, config);
    }

    public void storePom(MavenMetadata metadata, File file) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven pom from metadata : " + metadata);
        }

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
                .override(true);

        dormService.storeResource(resource, metadata, config);

        // read pom
        MavenMetadata metadata1 = new MavenMetadataBuilder()
                .groupId("group1")
                .artifactId("artifact1")
                .version("1.0")
                .build();

        MavenMetadata metadata2 = new MavenMetadataBuilder()
                .groupId("group2")
                .artifactId("artifact2")
                .version("2.0")
                .build();

        MavenMetadata metadata3 = new MavenMetadataBuilder()
                .groupId("group3")
                .artifactId("artifact3")
                .version("3.0")
                .build();

        List<DormMetadata> dependenciesMetadatas = new ArrayList<DormMetadata>();
        dependenciesMetadatas.add(metadata1);
        dependenciesMetadatas.add(metadata2);
        dependenciesMetadatas.add(metadata3);

        DependencyNode root = DefaultDependencyNode.create(DefaultDependency.create(metadata));

        for (DormMetadata dependencyMetadata : dependenciesMetadatas) {
            root.addChild(DefaultDependencyNode.create(DefaultDependency.create(dependencyMetadata)));
        }

        dormService.addDependenciesToNode(root);
    }


    public DormResource getArtifact(MavenMetadata metadata) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get maven artifact from metadata : " + metadata);
        }

        return dormService.getResource(metadata);
    }
}
