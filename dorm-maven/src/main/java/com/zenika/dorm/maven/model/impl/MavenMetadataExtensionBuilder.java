package com.zenika.dorm.maven.model.impl;

import com.zenika.dorm.core.model.DormWebServiceRequest;
import com.zenika.dorm.maven.helper.MavenSpecificHelper;
import com.zenika.dorm.maven.model.formatter.MavenFilenameFormatter;

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

    public MavenMetadataExtensionBuilder(DormWebServiceRequest request) {

        groupId = request.getProperty(MavenMetadataExtension.METADATA_GROUPID);
        artifactId = request.getProperty(MavenMetadataExtension.METADATA_ARTIFACTID);
        version = request.getProperty(MavenMetadataExtension.METADATA_VERSION);

        mavenMetadata = MavenSpecificHelper.isMavenMetadataFile(request.getFilename());
        if (mavenMetadata) {
            return;
        }

        MavenFilenameFormatter formatter = new MavenFilenameFormatter(request.getFilename());
        classifier = formatter.getClassifier();
        extension = formatter.getExtension();
        packaging = request.getProperty(MavenMetadataExtension.METADATA_PACKAGING);

        snapshot = MavenSpecificHelper.isSnapshot(version);
        if (snapshot) {
            timestamp = formatter.getTimestamp();
            buildNumber = formatter.getBuildNumber();
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
