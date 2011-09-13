package com.zenika.dorm.core.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.zenika.dorm.core.processor.ProcessorExtension;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormExtensionModule extends AbstractModule {

    protected MapBinder<String, ProcessorExtension> processorBinder;
    protected MapBinder<String, Class> metadataExtensionBinder;

    @Override
    protected void configure() {
        processorBinder = MapBinder.newMapBinder(binder(), String.class, ProcessorExtension.class);
        metadataExtensionBinder = MapBinder.newMapBinder(binder(), String.class, Class.class);
    }
}
