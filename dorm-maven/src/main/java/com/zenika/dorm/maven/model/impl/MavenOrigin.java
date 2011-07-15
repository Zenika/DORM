package com.zenika.dorm.maven.model.impl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

import com.zenika.dorm.core.model.DormOrigin;

/**
 * Maven extension point to the dorm model
 * Add maven specific metadatas
 *
 * todo: add packaging + classifier metadatas
 *
 */
public class MavenOrigin implements DormOrigin {

    public static final String ORIGIN = "maven";

    private String groupId;
    private String artifactId;
    private String versionId;
    private String type;

    public MavenOrigin(String groupId, String artifactId, String versionId, String type) {
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
