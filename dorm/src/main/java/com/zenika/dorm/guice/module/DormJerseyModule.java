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

//    private void bindResources() {
//
//        // web services
//        bind(DormResource.class);
//        bind(MavenResource.class);
//
//        // exception mappers
//        bind(CoreExceptionMapper.class);
//        bind(DormProcessExceptionMapper.class);
//    }

//    private void bindProcessor() {
//        DefaultProcessor processor = new DefaultProcessor();
//        processor.getExtensions().put(DefaultDormMetadataExtension.EXTENSION_NAME, new DormProcessor());
//        processor.getExtensions().put(MavenMetadataExtension.EXTENSION_NAME, new MavenProcessor());
//        bind(Processor.class).toInstance(processor);
//    }
//
//    private void bindService() {
//        bind(DormService.class).to(DefaultDormService.class);
//    }
//
//
//    private void bindRepository() {
//        bind(DormRepository.class).to(DefaultDormRepository.class);
//    }
}
