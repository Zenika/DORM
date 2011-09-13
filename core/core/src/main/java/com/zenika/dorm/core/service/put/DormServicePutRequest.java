package com.zenika.dorm.core.service.put;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormResource;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormServicePutRequest {

    private DependencyNode node;
    private DormResource resource;
    private DormServicePutValues values;

    public DormServicePutRequest(String processName, DependencyNode node) {
        this.node = node;
    }

    public DormServicePutRequest(String processName, DormResource resource) {
        this.resource = resource;
    }

    public boolean isEmpty() {
        return null == node && null == resource;
    }

    public DependencyNode getNode() {
        return node;
    }

    public void setNode(DependencyNode node) {
        this.node = node;
    }

    public DormResource getResource() {
        return resource;
    }

    public void setResource(DormResource resource) {
        this.resource = resource;
    }

    public DormServicePutValues getValues() {
        return values;
    }

    public void setValues(DormServicePutValues values) {
        this.values = values;
    }
}