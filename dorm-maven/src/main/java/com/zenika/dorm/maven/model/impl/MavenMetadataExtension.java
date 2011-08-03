package com.zenika.dorm.maven.model.impl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

import com.zenika.dorm.core.model.DormMetadataExtension;

/**
 * Maven immutable extension point to the dorm model
 * Add maven specific metadatas
 *
 * todo: add packaging + classifier metadatas
 */
public final class MavenMetadataExtension implements DormMetadataExtension {

    public static final String NAME = "maven";

    private final String groupId;
    private final String artifactId;
    private final String versionId;
    private final String type;

    public MavenMetadataExtension(String groupId, String artifactId, String versionId, String type) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.versionId = versionId;
        this.type = type;
    }

    @Override
    public String getQualifier() {
        return groupId + ":" + artifactId + ":" + versionId + ":" + type;
    }

    @Override
    public String getExtensionName() {
        return NAME;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersionId() {
        return versionId;
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
        if (versionId != null ? !versionId.equals(extension.versionId) : extension.versionId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        result = 31 * result + (versionId != null ? versionId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
