package com.zenika.dorm.core.service.impl.put;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.impl.DefaultDormServiceRequest;
import com.zenika.dorm.core.service.put.DormServicePutRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServicePutRequest extends DefaultDormServiceRequest
        implements DormServicePutRequest {

    private DependencyNode node;

    public DefaultDormServicePutRequest(String processName) {
        super(processName);
    }

    @Override
    public DependencyNode getNode() {
        return node;
    }

    public void setNode(DependencyNode node) {
        this.node = node;
    }
}
