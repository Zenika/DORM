package com.zenika.dorm.core.modelnew.impl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

import com.zenika.dorm.core.modelnew.DormOrigin;

/**
 * Should be moved to the dorm-maven project
 */
public class MavenOrigin implements DormOrigin {

    private static final String ORIGIN = "maven";

    private String groupId;
    private String artifactId;

    public MavenOrigin(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
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
