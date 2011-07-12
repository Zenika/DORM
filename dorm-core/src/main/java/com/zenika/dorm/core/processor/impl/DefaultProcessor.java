package com.zenika.dorm.core.processor.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.service.DormService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultProcessor implements Processor {

    private Map<String, ProcessorExtension> extensions = new HashMap<String, ProcessorExtension>();

    @Inject
    private DormService service;

    public Boolean push(DormMetadata metadata) {
        Dependency dependency = getExtension(metadata).push(metadata);
        return service.pushDependency(dependency);

    }

    private ProcessorExtension getExtension(DormMetadata metadata) {
        String origin = metadata.getOrigin().getOrigin();
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
