package com.zenika.dorm.maven.guice.module;

import com.google.inject.AbstractModule;
import com.zenika.dorm.maven.service.MavenService;
import com.zenika.dorm.maven.ws.resource.MavenResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenModule extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(MavenModule.class);

    @Override
    protected void configure() {

        if (LOG.isInfoEnabled()) {
            LOG.info("Configure maven guice module");
        }

        bind(MavenResource.class);
        bind(MavenService.class);
    }
}
