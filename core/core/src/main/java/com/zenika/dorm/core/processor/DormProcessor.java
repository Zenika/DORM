package com.zenika.dorm.core.processor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.processor.extension.ProcessorExtension;
import com.zenika.dorm.core.processor.extension.RequestAnalyser;
import com.zenika.dorm.core.service.DormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

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
     * Injected by guice
     */
    private Set<RequestAnalyser> requestAnalysers;

    public DormProcessor() {

    }

    @Inject
    public DormProcessor(Set<RequestAnalyser> requestAnalysers) {
        this.requestAnalysers = requestAnalysers;
    }

    public DormWebServiceResult push(DormWebServiceRequest request) {

        checkNotNull(request);
        LOG.debug("Push generic request : {}", request);

        return getExtension(request).pushFromGenericRequest(request);
    }

    public DormWebServiceResult get(DormWebServiceRequest request) {

        checkNotNull(request);
        LOG.debug("Get generic request : {}", request);

        return getExtension(request).getFromGenericRequest(request);
    }

    /**
     * Get extension processor from the origin name
     * Extensions are injected to the processor in the guice module config
     *
     * @param request
     * @return the extension corresponding to the origin
     */
    public ProcessorExtension getExtension(DormWebServiceRequest request) {

        for (RequestAnalyser analyser : requestAnalysers) {

            if (analyser.isKnownRequest(request)) {
                return analyser.getExtension();
            }
        }

        throw new CoreException("Processor extension not found for request : " + request);
    }
}

