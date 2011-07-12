package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;

/**
 * Should be renamed as DormMetadata
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormMetadata implements DormMetadata {

    private String qualifier;
    private String version;
    private DormOrigin origin;

    public DefaultDormMetadata(String version, DormOrigin origin) {
        this.version = version;
        this.origin = origin;
    }

    @Override
    public String getFullQualifier() {
        return getQualifier() + ":" + version + ":" + origin.getOrigin();
    }

    @Override
    public String getQualifier() {

        if (null == qualifier) {
            qualifier = origin.getQualifier();
        }

        return qualifier;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public DormOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(DormOrigin origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return getFullQualifier();
    }
}
