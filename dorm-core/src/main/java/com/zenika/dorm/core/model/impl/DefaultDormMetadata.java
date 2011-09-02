package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.util.DormFormatter;
import com.zenika.dorm.core.util.DormStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    /**
     * The metadata extension must be immutable
     */
    private final DormMetadataExtension extension;

    public static DefaultDormMetadata create(String version, DormMetadataExtension extension) {
        return new DefaultDormMetadata(version, extension);
    }

    private DefaultDormMetadata(String version, DormMetadataExtension extension) {

        if (null == extension ||
                DormStringUtils.oneIsBlank(extension.getQualifier(), extension.getExtensionName())) {
            throw new CoreException("Properties are missing for metadata");
        }

        this.version = StringUtils.defaultIfBlank(DormFormatter.formatMetadataVersion(version),
                "no-version");
        this.extension = extension;

        String extensionQualifier = DormFormatter.formatMetadataExtensionQualifier(extension.getQualifier());
        qualifier = DormFormatter.formatMetadataQualifier(extension.getExtensionName(),
                extensionQualifier, version);
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
        return new ToStringBuilder(this)
                .append("qualifier", qualifier)
                .append("version", version)
                .append("extension", extension)
                .appendSuper(super.toString())
                .toString();
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
        if (version != null ? !version.equals(metadata.version) : metadata.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = qualifier != null ? qualifier.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        return result;
    }
}