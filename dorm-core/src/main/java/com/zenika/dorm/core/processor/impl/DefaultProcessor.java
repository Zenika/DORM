package com.zenika.dorm.core.processor.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.service.DormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProcessor.class);

    /**
     * Extensions are injected in the guice module
     */
    private Map<String, ProcessorExtension> extensions = new HashMap<String, ProcessorExtension>();

    @Inject
    private DormService service;

    @Override
    public Boolean push(DormRequest request) {

        if (null == request) {
            throw new CoreException("Request is required");
        }

        DependencyNode node = getExtension(request).push(request);

        return service.push(node);
    }

    @Override
    public Dependency get(DormRequest request) {

        if (null == request) {
            throw new CoreException("Request is required");
        }

        DormMetadata metadata = getExtension(request).getMetadata(request);
        Usage usage = Usage.create(request.getUsage());

        LOG.info("get dependency for metadata : " + metadata + " and usage : " + usage);

        return service.getDependency(metadata, usage);
    }

    /**
     * Get extension processor from the origin name
     * Extensions are injected to the processor in the guice module config
     *
     * @param request
     * @return the extension corresponding to the origin
     */
    private ProcessorExtension getExtension(DormRequest request) {

        String origin = request.getOrigin();

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
