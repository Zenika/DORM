package com.zenika.dorm.maven.model.builder;

import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenMetadata;

/**
 * Builder for maven metadata extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataBuilder {

    private Long id;
    private String groupId;
    private String artifactId;
    private String version;
    private MavenBuildInfo buildInfo;

    public MavenMetadataBuilder(String artifactId) {
        this.artifactId = artifactId;
    }

    public MavenMetadataBuilder(MavenMetadata metadata) {
        this(metadata.getArtifactId());
        groupId(metadata.getGroupId());
        version(metadata.getVersion());
    }

    public MavenMetadataBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public MavenMetadataBuilder groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public MavenMetadataBuilder version(String version) {
        this.version = version;
        return this;
    }

    public MavenMetadataBuilder buildInfo(MavenBuildInfo buildInfo) {
        this.buildInfo = buildInfo;
        return this;
    }

    public MavenMetadata build() {
        return new MavenMetadata(id, groupId, artifactId, version, buildInfo);
    }
}
