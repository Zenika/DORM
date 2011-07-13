package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;

/**
 * Should be renamed as DormMetadata
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormMetadata implements DormMetadata {

    private String qualifier;
    private String fullQualifier;
    private String version;
    private DormOrigin origin;

    public DefaultDormMetadata(String version, DormOrigin origin) {

        // validate before create metadata
        if (null == version || null == origin || null == origin.getQualifier() || null == origin.getOrigin()) {
            throw new CoreException("properties are missing for metadata");
        }

        this.version = version;
        this.origin = origin;
    }

    @Override
    public String getFullQualifier() {

        if (null == fullQualifier) {
            fullQualifier = getQualifier() + ":" + version + ":" + origin.getOrigin();
        }

        return fullQualifier;
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

    @Override
    public String toString() {
        return getFullQualifier();
    }
}
