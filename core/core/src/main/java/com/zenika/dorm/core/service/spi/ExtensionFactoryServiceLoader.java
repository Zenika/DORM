package com.zenika.dorm.core.service.spi;

import com.google.inject.Singleton;
import com.zenika.dorm.core.factory.ExtensionMetadataFactory;

import java.util.ServiceLoader;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Singleton
public class ExtensionFactoryServiceLoader {

    private ServiceLoader<ExtensionMetadataFactory> loader;

    public ExtensionFactoryServiceLoader(){
        loader = ServiceLoader.load(ExtensionMetadataFactory.class);
    }

    public ExtensionMetadataFactory getInstanceOf(String extensionName){
        for (ExtensionMetadataFactory metadata:loader){
            if (metadata.getExtensionName().equals(extensionName)){
                return metadata;
            }
        }
        return null;
    }

}