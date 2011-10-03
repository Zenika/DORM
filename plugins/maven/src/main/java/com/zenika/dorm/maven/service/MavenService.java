package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.validator.FileValidator;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.pom.MavenPomReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;

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

        checkNotNull(metadata);
        FileValidator.validateFile(file);

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

        checkNotNull(metadata);
        FileValidator.validateFile(file);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven hash for metadata : " + metadata);
        }

        if (!hashService.compareHash(metadata, file)) {
            throw new MavenException("Invalid hash metadata : " + metadata);
        }

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
                .override(true);

        dormService.storeResource(resource, metadata, config);
    }

    public void storePom(MavenMetadata metadata, File file) {

        checkNotNull(metadata);
        FileValidator.validateFile(file);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven pom from metadata : " + metadata);
        }

        MavenPomReader reader = new MavenPomReader(file);
        MavenMetadata pomMetadata = reader.getArtifact();

        if (!metadata.equals(pomMetadata)) {
            throw new MavenException("Artifact to store and associated pom are different");
        }

        DependencyNode root = DefaultDependencyNode.create(DefaultDependency.create(metadata));

        for (Dependency dependency : reader.getDependencies()) {
            root.addChild(DefaultDependencyNode.create(dependency));
        }

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
                .override(true);

        dormService.storeResource(resource, metadata, config);
        dormService.addDependenciesToNode(root);
    }


    public DormResource getArtifact(MavenMetadata metadata) {

        checkNotNull(metadata);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get maven artifact from metadata : " + metadata);
        }

        return dormService.getResource(metadata);
    }
}
