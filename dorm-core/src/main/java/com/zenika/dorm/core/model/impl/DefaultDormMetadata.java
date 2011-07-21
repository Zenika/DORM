package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;

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
    private final DormMetadataExtension extension;

    public static DefaultDormMetadata create(String version, DormMetadataExtension extension) {
        return new DefaultDormMetadata(version, extension);
    }

    /**
     * @param version
     * @param extension
     * @deprecated Will be private, use the factory methods
     */
    public DefaultDormMetadata(String version, DormMetadataExtension extension) {

        if (null == version || null == extension || null == extension.getQualifier() || null == extension.getOrigin()) {
            throw new CoreException("Properties are missing for metadata");
        }

        this.version = version;
        this.extension = extension;
        this.qualifier = extension.getQualifier();
        this.fullQualifier = this.qualifier + ":" + version + ":" + extension.getOrigin();
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
    public DormMetadataExtension getExtension() {
        return extension;
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
        if (extension != null ? !extension.equals(metadata.extension) : metadata.extension != null) return false;
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
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        return result;
    }
}
