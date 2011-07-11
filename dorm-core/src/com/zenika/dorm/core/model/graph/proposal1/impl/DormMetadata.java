package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.modelnew.DormOrigin;

/**
 * Should be renamed as DormMetadata
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormMetadata {

    private String qualifier;
    private String version;
    private DormOrigin origin;

    public DormMetadata(String version, DormOrigin origin) {
        this.version = version;
        this.origin = origin;
    }

    public String getFullQualifier() {
        return getQualifier() + ":" + version + ":" + origin.getOrigin();
    }

    public String getQualifier() {

        if (null == qualifier) {
            qualifier = origin.getQualifier();
        }

        return qualifier;
    }

    public String getVersion() {
        return version;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DormMetadata dormMetadata = (DormMetadata) o;

        if (!origin.equals(dormMetadata.origin)) return false;
        if (!qualifier.equals(dormMetadata.qualifier)) return false;
        if (!version.equals(dormMetadata.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = qualifier.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + origin.hashCode();
        return result;
    }
}
