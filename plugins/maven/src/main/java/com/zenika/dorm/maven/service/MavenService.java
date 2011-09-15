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
    private DormService dormService;

    @Inject
    private MavenHashService hashService;

    public void storeMetadataWithArtifact(MavenMetadata metadata, File file) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store maven metadata with artifact : " + metadata);
        }

        DormResource resource = DefaultDormResource.create(file);
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
            LOG.debug("Store maven pom form metadata : " + metadata);
        }

        if (!hashService.compareMavenHashes(metadata, file)) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Pom to store is not equal to given hash");
            }
            return;
        }


    }


}
