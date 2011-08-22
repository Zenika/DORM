package com.zenika.dorm.maven.model.impl;

import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.maven.util.MavenFormatter;

/**
 * Builder for maven metadata extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataExtensionBuilder {

    private String groupId;
    private String artifactId;
    private String version;
    private String extension;
    private String classifier;
    private String packaging;
    private String timestamp;
    private String buildNumber;
    private boolean mavenMetadata;
    private boolean snapshot;

    public MavenMetadataExtensionBuilder(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public MavenMetadataExtensionBuilder(DormRequest request) {

        // required metadata
        groupId = MavenFormatter.formatGroupId(request.getProperty(MavenMetadataExtension.METADATA_GROUPID));
        artifactId = request.getProperty(MavenMetadataExtension.METADATA_ARTIFACTID);
        version = request.getProperty(MavenMetadataExtension.METADATA_VERSION);
        extension = MavenFormatter.getExtension(request.getFilename());

        mavenMetadata = MavenFormatter.isMavenMetadataFile(request.getFilename());
        if (mavenMetadata) {
            return;
        }

        packaging = request.getProperty(MavenMetadataExtension.METADATA_PACKAGING);
        classifier = MavenFormatter.getClassifierIfExists(request.getProperty(MavenMetadataExtension.METADATA_CLASSIFIER));

        snapshot = MavenFormatter.isSnapshot(version);
        if (snapshot) {
//            timestamp = MavenFormatter.get
//            buildNumber = MavenFormatter
        }


    }

    public MavenMetadataExtensionBuilder extension(String extension) {
        this.extension = extension;
        return this;
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

    public MavenMetadataExtensionBuilder buildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public MavenMetadataExtensionBuilder mavenMetadata(boolean mavenMetadata) {
        this.mavenMetadata = mavenMetadata;
        return this;
    }

    public MavenMetadataExtensionBuilder snapshot(boolean snapshot) {
        this.snapshot = snapshot;
        return this;
    }

    public MavenMetadataExtension build() {
        return new MavenMetadataExtension(groupId, artifactId, version, extension, packaging, classifier,
                timestamp, buildNumber, mavenMetadata, snapshot);
    }
}
