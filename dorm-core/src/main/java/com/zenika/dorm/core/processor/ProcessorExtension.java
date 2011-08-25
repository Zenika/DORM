package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.put.DormServicePutRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ProcessorExtension {

    public DormServicePutRequest buildPutRequest(DormRequest request);

    public DormServiceGetRequest buildGetRequest(DormRequest request);

    public Dependency buildDependency(DormServiceGetResult result);
}
