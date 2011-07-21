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

    public static final String ORIGIN = "maven";

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
    public String getOrigin() {
        return ORIGIN;
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
}
