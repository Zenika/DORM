package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.util.DormFormatter;

/**
 * Immutable dorm metadata
 *
 * Qualifier pattern : extension name / extension qualifier / version
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDormMetadata implements DormMetadata {

    private final String qualifier;
    private final String version;
    private final String type;

    /**
     * The metadata extension must be immutable
     */
    private final DormMetadataExtension extension;

    public static DefaultDormMetadata create(String version, String type, DormMetadataExtension extension) {
        return new DefaultDormMetadata(version, type, extension);
    }

    private DefaultDormMetadata(String version, String type, DormMetadataExtension extension) {

        if (null == version || null == extension || null == extension.getQualifier() || null == extension.getExtensionName()) {
            throw new CoreException("Properties are missing for metadata");
        }

        this.version = DormFormatter.formatMetadataVersion(version);
        this.type = DormFormatter.formatMetadataType(type);
        this.extension = extension;

        String extensionQualifier = DormFormatter.formatMetadataExtensionQualifier(extension.getQualifier());
        qualifier = DormFormatter.formatMetadataQualifier(extension.getExtensionName(),
                extensionQualifier, version, type);
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
    public String getType() {
        return type;
    }

    @Override
    public DormMetadataExtension getExtension() {
        return extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultDormMetadata metadata = (DefaultDormMetadata) o;

        if (extension != null ? !extension.equals(metadata.extension) : metadata.extension != null)
            return false;
        if (qualifier != null ? !qualifier.equals(metadata.qualifier) : metadata.qualifier != null)
            return false;
        if (type != null ? !type.equals(metadata.type) : metadata.type != null) return false;
        if (version != null ? !version.equals(metadata.version) : metadata.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = qualifier != null ? qualifier.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DormMetadata { " +
                "qualifier = " + qualifier +
                "type = " + type +
                " }";
    }
}