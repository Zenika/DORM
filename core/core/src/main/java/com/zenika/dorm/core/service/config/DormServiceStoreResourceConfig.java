package com.zenika.dorm.core.service.config;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("override", override)
                .appendSuper(super.toString())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DormServiceStoreResourceConfig that = (DormServiceStoreResourceConfig) o;

        if (override != that.override) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (override ? 1 : 0);
    }
}
