package com.zenika.dorm.maven.test.model.fixtures;

import com.zenika.dorm.maven.model.MavenFilename;
import com.zenika.dorm.maven.model.MavenUri;
import org.fest.assertions.Assertions;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFilenameFixture {

    private String classifier;
    private String extension;
    private String timestamp;
    private String buildNumber;

    private MavenFilename filename;

    public MavenFilenameFixture(String uri) {
        this.filename = new MavenUri(uri).getFilename();
    }

    public MavenFilenameFixture classifier(String classifier) {
        this.classifier = classifier;
        return this;
    }

    public MavenFilenameFixture extension(String extension) {
        this.extension = extension;
        return this;
    }

    public MavenFilenameFixture timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public MavenFilenameFixture buildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public void compare() {
        Assertions.assertThat(filename.getClassifier()).isEqualTo(classifier);
        Assertions.assertThat(filename.getExtension()).isEqualTo(extension);
        Assertions.assertThat(filename.getTimestamp()).isEqualTo(timestamp);
        Assertions.assertThat(filename.getBuildNumber()).isEqualTo(buildNumber);
    }

    public MavenFilename getFilename() {
        return filename;
    }

    public String getClassifier() {
        return classifier;
    }

    public String getExtension() {
        return extension;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBuildNumber() {
        return buildNumber;
    }
}
