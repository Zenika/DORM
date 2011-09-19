package com.zenika.dorm.core.service.spi;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.org.apache.bcel.internal.util.*;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.ws.resource.Dictionary;
import com.zenika.dorm.core.ws.resource.DormResource;
import groovy.lang.GroovyClassLoader;
import org.apache.commons.logging.Log;
import org.apache.tools.ant.AntClassLoader;

import java.io.File;
import java.lang.ClassLoader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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