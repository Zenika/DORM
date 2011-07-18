package com.zenika.dorm.core.processor;

import com.google.inject.Singleton;
import com.zenika.dorm.core.model.DormProperties;

import java.io.File;
import java.util.Map;

/**
 * Is this interface usefull ?
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public interface Processor {

    /**
     * @param origin
     * @param properties
     * @param file
     * @return
     * @deprecated Use push with dorm properties wrapper beside multiple objects and map
     */
    public Boolean push(String origin, Map<String, String> properties, File file);

    public Boolean push(DormProperties properties);

    public ProcessorHelper getHelper();
}
