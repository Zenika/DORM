package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.FileValidator;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenFilename;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.model.builder.MavenBuildInfoBuilder;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import com.zenika.dorm.maven.pom.MavenPomReader;
import org.apache.commons.lang3.StringUtils;
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
    private FileValidator fileValidator;

    @Inject
    private MavenHashService hashService;

    public void storeMetadataWithArtifact(MavenMetadata metadata, File file) {

        checkNotNull(metadata);
        fileValidator.validateFile(file);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven metadata with artifact : " + metadata);
        }

        new FileValidator().validateFile(file);
        DormResource resource = DefaultDormResource.create(
                metadata.getName() + "." + metadata.getBuildInfo().getExtension(), file);

        DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
                .override(true);

        dormService.storeMetadata(getEntityMavenMetadata(metadata));
        dormService.storeMetadata(metadata);
        dormService.storeResource(resource, metadata, config);
    }

    public void storeHash(MavenMetadata metadata, File file) {

        checkNotNull(metadata);
        fileValidator.validateFile(file);

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
        fileValidator.validateFile(file);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven pom from metadata : " + metadata);
        }

        MavenPomReader reader = new MavenPomReader(file);
        MavenMetadata pomMetadata = reader.getArtifact();

        if (!metadata.equalsEntityMetadataOnly(pomMetadata)) {

            if (LOG.isInfoEnabled()) {
                LOG.info("pom metadata = " + pomMetadata);
                LOG.info("uri metadata = " + metadata);
            }

            throw new MavenException("Artifact to store and associated pom are different");
        }

        DependencyNode root = DefaultDependencyNode.create(DefaultDependency.create(getEntityMavenMetadata(metadata)));

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

        DormResource resource = dormService.getResource(metadata);

        return resource;
    }

    private MavenMetadata getEntityMavenMetadata(MavenMetadata metadata) {

        MavenBuildInfo buildInfo = null;
        if (null != metadata.getBuildInfo()) {
            buildInfo = new MavenBuildInfoBuilder(metadata.getBuildInfo())
                    .extension(null)
                    .buildNumber(null)
                    .timestamp(null)
                    .build();
        }

        return new MavenMetadataBuilder(metadata)
                .buildInfo(buildInfo)
                .build();
    }

    public MavenMetadata buildMavenMetadata(MavenUri uri) {

        MavenFilename filename = uri.getFilename();
        MavenBuildInfoBuilder buildInfoBuilder = new MavenBuildInfoBuilder()
                .extension(filename.getExtension());

        if (StringUtils.isNotBlank(filename.getClassifier())) {
            buildInfoBuilder.classifier(filename.getClassifier());
        }

        if (StringUtils.isNotBlank(filename.getTimestamp()) && StringUtils.isNotBlank(filename
                .getBuildNumber())) {
            buildInfoBuilder.timestamp(filename.getTimestamp());
            buildInfoBuilder.buildNumber(filename.getBuildNumber());
        }

        MavenMetadataBuilder builder = new MavenMetadataBuilder()
                .artifactId(uri.getArtifactId())
                .groupId(uri.getGroupId())
                .version(uri.getVersion())
                .buildInfo(buildInfoBuilder.build());

        return builder.build();
    }


    public boolean isUseProxy(DormResource dormResource) {
        return dormResource == null;
    }

}
