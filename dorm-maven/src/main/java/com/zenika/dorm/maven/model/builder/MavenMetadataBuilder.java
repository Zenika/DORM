package com.zenika.dorm.maven.model.builder;

import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;

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
    protected String packaging;
    protected String timestamp;
    protected String buildNumber;
    protected String url;
    protected boolean mavenMetadata;
    protected boolean snapshot;

    public MavenMetadataBuilder(String artifactId) {
        this.artifactId = artifactId;
    }

    public MavenMetadataBuilder(MavenUri uri) {
        
    }

    public MavenMetadataBuilder groupId(String groupId) {
        this.groupId = groupId;
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

    public MavenMetadataBuilder packaging(String packaging) {
        this.packaging = packaging;
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

    public MavenMetadataBuilder url(String url) {
        this.url = url;
        return this;
    }

    public MavenMetadataBuilder mavenMetadata(boolean mavenMetadata) {
        this.mavenMetadata = mavenMetadata;
        return this;
    }

    public MavenMetadataBuilder snapshot(boolean snapshot) {
        this.snapshot = snapshot;
        return this;
    }

    public MavenMetadataExtension build() {
        return new MavenMetadataExtension(groupId, artifactId, version, extension, packaging, classifier,
                timestamp, buildNumber, url, mavenMetadata, snapshot);
    }
}
