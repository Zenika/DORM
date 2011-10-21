package com.zenika.dorm.maven.test.fixtures;

import com.zenika.dorm.maven.model.MavenPluginMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataFixtures {

    private MavenPluginMetadata commonsioMetadata;
    private MavenPluginMetadata commonsioPomMetadata;

    private MavenPluginMetadata junitMetadata;

    public MavenMetadataFixtures() {

        commonsioMetadata = getCommonsio();

        commonsioPomMetadata = getCommonsio();
        

        junitMetadata = getJunitBuilder();
    }

    private MavenPluginMetadata getCommonsio() {
        MavenPluginMetadata mavenPluginMetadata = new MavenPluginMetadata();
        mavenPluginMetadata.setArtifactId("commons-io");
        mavenPluginMetadata.setGroupId("commons-io");
        mavenPluginMetadata.setVersion("2.0.1");
        return mavenPluginMetadata;
    }

    private MavenPluginMetadata getJunitBuilder() {
        MavenPluginMetadata mavenPluginMetadata = new MavenPluginMetadata();
        mavenPluginMetadata.setArtifactId("junit");
        mavenPluginMetadata.setGroupId("junit");
        mavenPluginMetadata.setVersion("4.8.2");
        return mavenPluginMetadata;
    }

    public MavenPluginMetadata getCommonsioMetadata() {
        return commonsioMetadata;
    }

    public MavenPluginMetadata getCommonsioPomMetadata() {
        return commonsioPomMetadata;
    }

    public MavenPluginMetadata getJunitMetadata() {
        return junitMetadata;
    }
}
