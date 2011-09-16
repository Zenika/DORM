package com.zenika.dorm.core.util;

import com.google.inject.Inject;
import com.sun.org.apache.bcel.internal.util.*;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.ws.resource.Dictionary;
import com.zenika.dorm.core.ws.resource.DormResource;
import groovy.lang.GroovyClassLoader;
import org.apache.commons.logging.Log;
import org.apache.tools.ant.AntClassLoader;

import java.lang.ClassLoader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ServiceLoader;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ModuleService {

    private ServiceLoader<Dictionary> loader;

    private URL[] urls = null;

    public ModuleService() {
        try {
            urls = new URL[] {new URL("file:/home/erouan/test/")};
            ClassLoader classLoader = new URLClassLoader(urls);
            URL url = classLoader.getResource("ExtendedDictionary.jar");
            classLoader.loadClass("com.example.dictionary.ExtendedDictionary");
            System.out.println("Url: " + url);
            loader = ServiceLoader.load(Dictionary.class, classLoader);
            
        } catch (MalformedURLException e) {
            throw new CoreException("Error: ", e);
        } catch (ClassNotFoundException e) {
            throw new CoreException("Class not found: ", e);
        }
    }

    public Dictionary getModuleResource(String extensionName){
        for (Dictionary resource : loader){
            if (resource.getExtensionName().equals(extensionName)){
                return resource;
            }
        }
        return null;
    }
}
