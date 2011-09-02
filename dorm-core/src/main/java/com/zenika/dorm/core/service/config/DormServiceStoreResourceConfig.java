package com.zenika.dorm.core.service.config;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormServiceStoreResourceConfig extends
        DormServiceResourceConfig<DormServiceStoreResourceConfig> {

    private boolean override;

    @Override
    protected DormServiceStoreResourceConfig self() {
        return this;
    }

    public DormServiceStoreResourceConfig override(boolean override) {
        this.override = override;
        return this;
    }

    public boolean isOverride() {
        return override;
    }
}
