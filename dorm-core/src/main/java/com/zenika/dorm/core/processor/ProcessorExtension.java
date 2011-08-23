package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ProcessorExtension {

    public DependencyNode push(DormRequest request);

    public DormServiceGetRequest buildGetRequest(DormRequest request);

    public Dependency getDependency(DependencyNode node);
}
