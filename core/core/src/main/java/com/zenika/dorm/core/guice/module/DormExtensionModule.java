package com.zenika.dorm.core.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.processor.extension.RequestAnalyser;

/**
 * Extension can extends this module to inject their extension points.
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormExtensionModule extends AbstractModule {

    protected Multibinder<RequestAnalyser> userAgentAnalyserBinder;

    protected MapBinder<String, ExtensionMetadataFactory> metadataFactories;

    @Override
    protected void configure() {
        userAgentAnalyserBinder = Multibinder.newSetBinder(binder(), RequestAnalyser.class);
        metadataFactories = MapBinder.newMapBinder(binder(), String.class, ExtensionMetadataFactory.class);
    }
}