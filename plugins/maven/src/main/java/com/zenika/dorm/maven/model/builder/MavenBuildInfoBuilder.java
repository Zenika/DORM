package com.zenika.dorm.maven.model.builder;

import com.zenika.dorm.maven.model.MavenBuildInfo;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenBuildInfoBuilder {

    private String extension;
    private String classifier;
    private String timestamp;
    private String buildNumber;

    public MavenBuildInfoBuilder() {
    }

    public MavenBuildInfoBuilder(MavenBuildInfo buildInfo) {
        extension(buildInfo.getExtension());
        classifier(buildInfo.getClassifier());
        timestamp(buildInfo.getTimestamp());
        buildNumber(buildInfo.getBuildNumber());
    }

    public MavenBuildInfoBuilder extension(String extension) {
        this.extension = extension;
        return this;
    }

    public MavenBuildInfoBuilder classifier(String classifier) {
        this.classifier = classifier;
        return this;
    }

    public MavenBuildInfoBuilder timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public MavenBuildInfoBuilder buildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public MavenBuildInfo build() {
        return new MavenBuildInfo(extension, classifier, timestamp, buildNumber);
    }
}
