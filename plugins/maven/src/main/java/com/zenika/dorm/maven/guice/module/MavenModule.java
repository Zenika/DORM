package com.zenika.dorm.maven.guice.module;

import com.zenika.dorm.core.guice.module.DormExtensionModule;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.service.MavenService;
import com.zenika.dorm.maven.ws.resource.MavenResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenModule extends DormExtensionModule {

    private static final Logger LOG = LoggerFactory.getLogger(MavenModule.class);

    @Override
    protected void configure() {
        super.configure();

        if (LOG.isInfoEnabled()) {
            LOG.info("Configure maven guice module");
        }

        bind(MavenResource.class);
        bind(MavenService.class);

        processorBinder.addBinding(MavenMetadata.EXTENSION_NAME).to(MavenProcessor.class);
    }
}
