package com.zenika.dorm.core.service.put;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.DormServiceRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormServicePutRequest extends DormServiceRequest {

    public DependencyNode getNode();
}
