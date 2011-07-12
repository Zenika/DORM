package com.zenika.dorm.maven.model.impl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

import com.zenika.dorm.core.model.DormOrigin;

/**
 * Maven extension point to the dorm model
 * Add maven specific metadatas
 */
public class MavenOrigin implements DormOrigin {

    private static final String ORIGIN = "maven";

    private String groupId;
    private String artifactId;
    private String versionId;

    public MavenOrigin(String groupId, String artifactId, String versionId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.versionId = versionId;
    }

    @Override
    public String getQualifier() {
        return groupId + ":" + artifactId;
    }

    @Override
    public String getOrigin() {
        return ORIGIN;
    }
}
