package com.zenika.dorm.maven.model.impl;

/**
 * Builder for maven metadata extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataExtensionBuilder {

    private String groupId;
    private String artifactId;
    private String version;
    private String classifier;
    private String packaging;
    private String timestamp;
    private boolean mavenMetadata;

    public MavenMetadataExtensionBuilder(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public MavenMetadataExtensionBuilder classifier(String classifier) {
        this.classifier = classifier;
        return this;
    }

    public MavenMetadataExtensionBuilder packaging(String packaging) {
        this.packaging = packaging;
        return this;
    }

    public MavenMetadataExtensionBuilder timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public MavenMetadataExtensionBuilder mavenMetadata(boolean mavenMetadata) {
        this.mavenMetadata = mavenMetadata;
        return this;
    }

    public MavenMetadataExtension build() {
        return new MavenMetadataExtension(groupId, artifactId, version, packaging, classifier, timestamp,
                mavenMetadata);
    }
}
