package com.zenika.dorm.core.service.spi;

import com.google.inject.Singleton;
import com.zenika.dorm.core.factory.PluginExtensionMetadataFactory;

import java.util.ServiceLoader;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Singleton
public class ExtensionFactoryServiceLoader {

    private ServiceLoader<PluginExtensionMetadataFactory> loader;

    public ExtensionFactoryServiceLoader(){
        loader = ServiceLoader.load(PluginExtensionMetadataFactory.class);
    }

    public PluginExtensionMetadataFactory getInstanceOf(String extensionName){
        for (PluginExtensionMetadataFactory metadataPlugin :loader){
            if (metadataPlugin.getExtensionName().equals(extensionName)){
                return metadataPlugin;
            }
        }
        return null;
    }

}