package com.zenika.dorm.guice.module;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormJerseyModule extends JerseyServletModule {

    private static final Logger LOG = LoggerFactory.getLogger(DormJerseyModule.class);

    @Override
    protected void configureServlets() {

        if (LOG.isInfoEnabled()) {
            LOG.info("Configure dorm jersey guice module");
        }

        serve("/*").with(GuiceContainer.class);
    }
}
