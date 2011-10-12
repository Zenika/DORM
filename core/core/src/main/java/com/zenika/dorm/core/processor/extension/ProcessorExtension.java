package com.zenika.dorm.core.processor.extension;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.service.DormService;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class ProcessorExtension {
//
//    @Inject
//    protected DormService service;

    public abstract DormWebServiceResult pushFromGenericRequest(DormWebServiceRequest request);

    public abstract DormWebServiceResult push(DormWebServiceRequest request);

    public abstract DormWebServiceResult getFromGenericRequest(DormWebServiceRequest request);

    public abstract DormWebServiceResult get(DormWebServiceRequest request);
}
