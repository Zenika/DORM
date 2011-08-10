package com.zenika.dorm.maven.model.impl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.maven.exception.MavenException;

import java.util.Map;

/**
 * Maven immutable extension point to the dorm model
 * Add maven specific metadatas
 *
 * todo: add packaging + classifier metadatas
 */
public final class MavenMetadataExtension implements DormMetadataExtension {

    public static final String EXTENSION_NAME = "maven";

    /**
     * Metadata names
     */
    public static final String METADATA_GROUPID = "groupId";
    public static final String METADATA_ARTIFACTID = "artifactId";
    public static final String METADATA_VERSION = "version";
    public static final String METADATA_TYPE = "type";

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String type;

    public MavenMetadataExtension(String groupId, String artifactId, String version, String type) {

        if (null == groupId || null == artifactId || null == version || null == type) {
            throw new MavenException("Following metadatas are required : groupId, artifactId, versionId, type");
        }

        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.type = type;
    }

    @Override
    public String getQualifier() {
        return groupId + ":" + artifactId + ":" + version + ":" + type;
    }

    @Override
    public String getExtensionName() {
        return EXTENSION_NAME;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MavenMetadataExtension)) return false;

        MavenMetadataExtension extension = (MavenMetadataExtension) o;

        if (artifactId != null ? !artifactId.equals(extension.artifactId) : extension.artifactId != null)
            return false;
        if (groupId != null ? !groupId.equals(extension.groupId) : extension.groupId != null) return false;
        if (type != null ? !type.equals(extension.type) : extension.type != null) return false;
        if (version != null ? !version.equals(extension.version) : extension.version != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public DormMetadataExtension createFromMap(Map<String, String> properties) {
        return new MavenMetadataExtension(properties.get(METADATA_GROUPID),
                properties.get(METADATA_ARTIFACTID), properties.get(METADATA_VERSION),
                properties.get(METADATA_TYPE));
    }
}
