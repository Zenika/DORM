package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;

/**
 * Immutable dorm metadata
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDormMetadata implements DormMetadata {

    private final String qualifier;
    private final String fullQualifier;
    private final String version;

    /**
     * todo: Probem here, cannot be sure that all implementations will be immutable.
     */
    private final DormOrigin origin;

    public static DefaultDormMetadata create(String version, DormOrigin origin) {
        return new DefaultDormMetadata(version, origin);
    }

    /**
     * @param version
     * @param origin
     * @deprecated Will be private, use the factory methods
     */
    public DefaultDormMetadata(String version, DormOrigin origin) {

        if (null == version || null == origin || null == origin.getQualifier() || null == origin.getOrigin()) {
            throw new CoreException("Properties are missing for metadata");
        }

        this.version = version;
        this.origin = origin;
        this.qualifier = origin.getQualifier();
        this.fullQualifier = this.qualifier + ":" + version + ":" + origin.getOrigin();
    }

    @Override
    public String getFullQualifier() {
        return fullQualifier;
    }

    @Override
    public String getQualifier() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultDormMetadata)) return false;

        DefaultDormMetadata metadata = (DefaultDormMetadata) o;

        if (fullQualifier != null ? !fullQualifier.equals(metadata.fullQualifier) : metadata.fullQualifier != null)
            return false;
        if (origin != null ? !origin.equals(metadata.origin) : metadata.origin != null) return false;
        if (qualifier != null ? !qualifier.equals(metadata.qualifier) : metadata.qualifier != null)
            return false;
        if (version != null ? !version.equals(metadata.version) : metadata.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = qualifier != null ? qualifier.hashCode() : 0;
        result = 31 * result + (fullQualifier != null ? fullQualifier.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        return result;
    }
}
