package com.zenika.dorm.maven.model.builder;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.builder.DormMetadataBuilder;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenMetadata;

/**
 * Builder for maven metadata extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataBuilder extends DormMetadataBuilder<MavenMetadataBuilder> {

    private String groupId;
    private String artifactId;
    private String version;
    private MavenBuildInfo buildInfo;

    public MavenMetadataBuilder() {
    }

    public MavenMetadataBuilder(MavenMetadata metadata) {
        super(metadata);
        artifactId(metadata.getArtifactId());
        groupId(metadata.getGroupId());
        version(metadata.getVersion());
        buildInfo(metadata.getBuildInfo());
    }

    @Override
    protected MavenMetadataBuilder self() {
        return this;
    }

    public MavenMetadataBuilder artifactId(String artifactId) {
        this.artifactId = artifactId;
        return self();
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
