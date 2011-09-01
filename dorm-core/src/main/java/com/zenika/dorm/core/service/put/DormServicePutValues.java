package com.zenika.dorm.core.service.put;

import com.zenika.dorm.core.service.DormServiceValues;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormServicePutValues extends DormServiceValues {

    private String resourcePath;
    private boolean resourceInternal;

    public DormServicePutValues(String extensionName) {
        super(extensionName);
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public boolean isResourceInternal() {
        return resourceInternal;
    }

    public void setResourceInternal(boolean resourceInternal) {
        this.resourceInternal = resourceInternal;
    }
}

