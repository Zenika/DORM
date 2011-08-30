package com.zenika.dorm.core.processor.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormWebServiceRequest;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.put.DormServicePutRequest;
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
    public Boolean push(DormWebServiceRequest request) {

        if (null == request) {
            throw new CoreException("Request is required");
        }

        DormServicePutRequest putRequest = getExtension(request).buildPutRequest(request);
        service.put(putRequest);

        return true;
    }

    @Override
    public Dependency get(DormWebServiceRequest request) {

        if (null == request) {
            throw new CoreException("Request is required");
        }

        ProcessorExtension processorExtension = getExtension(request);

        DormServiceGetRequest getRequest = processorExtension.buildGetRequest(request);

        Usage usage = Usage.create(request.getUsage());
        getRequest.getValues().setUsage(usage);

        if (LOG.isInfoEnabled()) {
            LOG.info("Get request : " + getRequest);
        }

        DormServiceGetResult getResult = service.get(getRequest);

        if (LOG.isInfoEnabled()) {
            LOG.info("Get result : " + getResult);
        }

        Dependency dependency = processorExtension.buildDependency(getResult);

        if (LOG.isInfoEnabled()) {
            LOG.info("Dependency from get result : " + dependency);
        }

        return dependency;
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
