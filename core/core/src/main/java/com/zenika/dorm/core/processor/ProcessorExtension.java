package com.zenika.dorm.core.processor;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.put.DormServicePutRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class ProcessorExtension {

    @Inject
    protected DormService service;

    public abstract DormWebServiceResult push(DormWebServiceRequest request);

    public abstract DormWebServiceResult get(DormWebServiceRequest request);

//    public abstract DormServicePutRequest buildPutRequest(DormWebServiceRequest request);
//
//    public abstract DormServiceGetRequest buildGetRequest(DormWebServiceRequest request);
//
//    public abstract Dependency buildDependency(DormServiceGetResult result);
}
