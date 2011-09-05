package com.zenika.dorm.core.service.config;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.util.DormStringUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormServiceResourceConfig<T extends DormServiceResourceConfig> {

    protected String resourcePath;
    protected String extensionName;
    protected DormMetadata metadata;

    protected abstract T self();

    public T resourcePath(String resourcePath, String extensionName) {
        this.resourcePath = resourcePath;
        this.extensionName = extensionName;
        return self();
    }

    public T metadata(DormMetadata metadata) {
        this.metadata = metadata;
        return self();
    }

    public boolean isInternalResource() {
        return !DormStringUtils.oneIsBlank(extensionName, resourcePath);
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public DormMetadata getMetadata() {
        return metadata;
    }
}
