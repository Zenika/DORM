package com.zenika.dorm.core.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.processor.ProcessorExtension;

/**
 * Extension can extends this module to inject their extension points.
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormExtensionModule extends AbstractModule {

    protected MapBinder<String, ProcessorExtension> processorBinder;
    protected MapBinder<String, Class> metadataExtensionBinder;
    protected MapBinder<String, ExtensionMetadataFactory> metadataFactories;

    @Override
    protected void configure() {
        processorBinder = MapBinder.newMapBinder(binder(), String.class, ProcessorExtension.class);
        metadataFactories = MapBinder.newMapBinder(binder(), String.class, ExtensionMetadataFactory.class);

        // should be deprecated
        metadataExtensionBinder = MapBinder.newMapBinder(binder(), String.class, Class.class);
    }
}