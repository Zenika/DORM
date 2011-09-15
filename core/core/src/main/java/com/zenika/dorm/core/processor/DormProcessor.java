package com.zenika.dorm.core.processor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
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
public class DormProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DormProcessor.class);

    /**
     * Extensions are injected in the guice module
     */
    private Map<String, ProcessorExtension> extensions = new HashMap<String, ProcessorExtension>();

    @Inject
    private DormService service;

    public DormProcessor() {
    }

    @Inject
    public DormProcessor(Map<String, ProcessorExtension> extensions) {
        this.extensions = extensions;
    }

    public DormWebServiceResult push(DormWebServiceRequest request) {

        if (null == request) {
            throw new CoreException("Request is required");
        }

        return getExtension(request).push(request);
    }

    public DormWebServiceResult get(DormWebServiceRequest request) {

        if (null == request) {
            throw new CoreException("Request is required");
        }

        return getExtension(request).get(request);
    }

    /**
     * Get extension processor from the origin name
     * Extensions are injected to the processor in the guice module config
     *
     * @param request
     * @return the extension corresponding to the origin
     */
    private ProcessorExtension getExtension(DormWebServiceRequest request) {

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

