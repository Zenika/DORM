package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.maven.model.MavenMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenService {

    private static final Logger LOG = LoggerFactory.getLogger(MavenService.class);

    @Inject
    private DormService service;

    public void storeMetadataWithArtifact(MavenMetadata metadata, File file) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven metadata with artifact : " + metadata);
        }

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig storeResourceConfig = new DormServiceStoreResourceConfig()
                .override(true)
                .metadata(metadata);

        service.storeMetadata(metadata);
        service.storeResource(resource, storeResourceConfig);
    }

    public void storeArtifact(MavenMetadata metadata, File file) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven artifact for metadata : " + metadata);
        }

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig storeResourceConfig = new DormServiceStoreResourceConfig()
                .override(true)
                .metadata(metadata);

        service.storeResource(resource, storeResourceConfig);
    }

    public void storePom(MavenMetadata mavenMetadata, File file) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven pom form metadata : " + mavenMetadata);
        }

    }
}
