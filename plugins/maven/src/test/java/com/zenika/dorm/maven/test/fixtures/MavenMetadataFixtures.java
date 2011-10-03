package com.zenika.dorm.maven.test.fixtures;

import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenBuildInfoBuilder;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataFixtures {

    private MavenMetadata commonsioMetadata;
    private MavenMetadata commonsioPomMetadata;

    private MavenMetadata junitMetadata;

    public MavenMetadataFixtures() {

        commonsioMetadata = getCommonsioBuilder().build();

        commonsioPomMetadata = getCommonsioBuilder()
                .buildInfo(new MavenBuildInfoBuilder()
                        .extension("pom")
                        .build())
                .build();

        junitMetadata = getJunitBuilder().build();
    }

    private MavenMetadataBuilder getCommonsioBuilder() {
        return new MavenMetadataBuilder()
                .artifactId("commons-io")
                .groupId("commons-io")
                .version("2.0.1");
    }

    private MavenMetadataBuilder getJunitBuilder() {
        return new MavenMetadataBuilder()
                .artifactId("junit")
                .groupId("junit")
                .version("4.8.2");
    }

    public MavenMetadata getCommonsioMetadata() {
        return commonsioMetadata;
    }

    public MavenMetadata getCommonsioPomMetadata() {
        return commonsioPomMetadata;
    }

    public MavenMetadata getJunitMetadata() {
        return junitMetadata;
    }
}
