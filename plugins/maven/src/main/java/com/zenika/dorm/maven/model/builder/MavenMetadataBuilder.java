package com.zenika.dorm.maven.model.builder;

import com.zenika.dorm.maven.model.MavenMetadata;

/**
 * Builder for maven metadata extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataBuilder {

    protected String groupId;
    protected String artifactId;
    protected String version;
    protected String extension;
    protected String classifier;
    protected String timestamp;
    protected String buildNumber;
    protected boolean snapshot;

    public MavenMetadataBuilder(String artifactId) {
        this.artifactId = artifactId;
    }

    public MavenMetadataBuilder(MavenMetadata metadata) {
        this(metadata.getArtifactId());
        groupId(metadata.getGroupId());
        version(metadata.getVersion());
        extension(metadata.getExtension());
        classifier(metadata.getClassifier());
        timestamp(metadata.getTimestamp());
        buildNumber(metadata.getBuildNumber());
        snapshot(metadata.isSnapshot());
    }

    public MavenMetadataBuilder groupId(String groupId) {
        this.groupId = groupId; //.replaceAll("/", "_");
        return this;
    }

    public MavenMetadataBuilder version(String version) {
        this.version = version;
        return this;
    }

    public MavenMetadataBuilder extension(String extension) {
        this.extension = extension;
        return this;
    }

    public MavenMetadataBuilder classifier(String classifier) {
        this.classifier = classifier;
        return this;
    }

    public MavenMetadataBuilder timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public MavenMetadataBuilder buildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public MavenMetadataBuilder snapshot(boolean snapshot) {
        this.snapshot = snapshot;
        return this;
    }

    public MavenMetadata build() {
        return new MavenMetadata(groupId, artifactId, version, extension, classifier,
                timestamp, buildNumber, snapshot);
    }
}
