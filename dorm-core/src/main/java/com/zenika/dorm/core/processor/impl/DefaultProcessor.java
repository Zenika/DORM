package com.zenika.dorm.core.processor.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.service.DormService;

import java.util.HashMap;
import java.util.Map;

/**
 * Main processor which delegate to the appropriate extension and then call the service to interact with
 * stored dependencies in the persistence layer and/or the file system repository
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public class DefaultProcessor implements Processor {

    /**
     * Extensions are injected in the guice module
     */
    private Map<String, ProcessorExtension> extensions = new HashMap<String, ProcessorExtension>();

    @Inject
    private DormService service;

    @Override
    public Boolean push(DormRequest request) {

        if (null == request) {
            throw new CoreException("Properties are null or incomplete");
        }

        DependencyNode node = getExtension(request.getOrigin()).push(request);

        return service.pushNode(node);
    }

    /**
     * Get extension processor from the origin name
     * Extensions are injected to the processor in the guice module config
     *
     * @param origin
     * @return the extension corresponding to the origin
     */
    private ProcessorExtension getExtension(String origin) {

        ProcessorExtension extension = extensions.get(origin);

        if (null == extension) {
            throw new CoreException("Extension " + origin + " not found");
        }

        return extension;
    }

    public Map<String, ProcessorExtension> getExtensions() {
        return extensions;
    }
}
