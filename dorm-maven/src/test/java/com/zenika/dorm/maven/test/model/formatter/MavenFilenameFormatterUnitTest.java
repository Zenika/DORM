package com.zenika.dorm.maven.test.model.formatter;

import com.zenika.dorm.maven.exception.MavenFormatterException;
import com.zenika.dorm.maven.model.formatter.MavenFilenameFormatter;
import com.zenika.dorm.maven.model.impl.MavenConstant;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFilenameFormatterUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFilenameFormatterUnitTest.class);

    private String extension;
    private String artifactId;
    private String version;
    private String versionWithSnapshot;
    private String timestamp;
    private String buildNumber;
    private String classifier;
    private String extension2;

    private String filename1;
    private String filename2;
    private String filename3;
    private String filename4;
    private String filename5;

    private String badFilename1;
    private String badFilename2;
    private String badFilename3;
    private String badFilename4;

    @Before
    public void before() {

        artifactId = "foo-bar";
        version = "1.0";
        versionWithSnapshot = version + "-SNAPSHOT";
        timestamp = "20110822.152325";
        buildNumber = "1";
        classifier = "test";
        extension = MavenConstant.FileExtension.JAR;
        extension2 = MavenConstant.FileExtension.SHA1;

        filename1 = artifactId + "-" + version + "-" + timestamp + "-" + buildNumber + "." + extension;
        filename2 = artifactId + "-" + version + "-" + timestamp + "-" + buildNumber + "-" + classifier +
                "." + extension;
        filename3 = artifactId + "-" + versionWithSnapshot + "-" + timestamp + "-" + buildNumber + "." + extension;
        filename4 = artifactId + "-" + versionWithSnapshot + "-" + timestamp + "-" + buildNumber + "-" + classifier +
                "." + extension;
        filename5 = artifactId + "-" + versionWithSnapshot + "-" + timestamp + "-" + buildNumber + "-" + classifier +
                "." + extension + "." + extension2;

        badFilename1 = version + "-" + timestamp + "-" + buildNumber + "." + extension;
        badFilename2 = artifactId + "-" + version + "-" + timestamp + "-" + buildNumber;
        badFilename3 = artifactId + "-" + timestamp + "-" + buildNumber + "-" + classifier + "." + extension;
        badFilename4 = artifactId + "-" + versionWithSnapshot + "-" + buildNumber + "-" + timestamp + "-" +
                classifier + "." + extension;

    }

    @Test
    public void formatValidFilenameWithoutClassifier() {

        LOG.debug("Test filename to format : " + filename1);

        MavenFilenameFormatter formatter = new MavenFilenameFormatter(filename1);

        Assertions.assertThat(formatter.getArtifactId()).isEqualTo(artifactId);
        Assertions.assertThat(formatter.getVersion()).isEqualTo(version);
        Assertions.assertThat(formatter.getTimestamp()).isEqualTo(timestamp);
        Assertions.assertThat(formatter.getBuildNumber()).isEqualTo(buildNumber);
        Assertions.assertThat(formatter.getExtension()).isEqualTo(extension);
    }

    @Test
    public void formatValidFilenameWithClassifier() {

        LOG.debug("Test filename to format : " + filename2);

        MavenFilenameFormatter formatter = new MavenFilenameFormatter(filename2);

        Assertions.assertThat(formatter.getArtifactId()).isEqualTo(artifactId);
        Assertions.assertThat(formatter.getVersion()).isEqualTo(version);
        Assertions.assertThat(formatter.getTimestamp()).isEqualTo(timestamp);
        Assertions.assertThat(formatter.getBuildNumber()).isEqualTo(buildNumber);
        Assertions.assertThat(formatter.getClassifier()).isEqualTo(classifier);
        Assertions.assertThat(formatter.getExtension()).isEqualTo(extension);
    }

    @Test
    public void formatValidFilenameWithSnapshot() {

        LOG.debug("Test filename to format : " + filename3);

        MavenFilenameFormatter formatter = new MavenFilenameFormatter(filename3);

        Assertions.assertThat(formatter.getArtifactId()).isEqualTo(artifactId);
        Assertions.assertThat(formatter.getVersion()).isEqualTo(versionWithSnapshot);
        Assertions.assertThat(formatter.getTimestamp()).isEqualTo(timestamp);
        Assertions.assertThat(formatter.getBuildNumber()).isEqualTo(buildNumber);
        Assertions.assertThat(formatter.getExtension()).isEqualTo(extension);
    }

    @Test
    public void formatValidFilenameWithSnapshotAndClassifier() {

        LOG.debug("Test filename to format : " + filename4);

        MavenFilenameFormatter formatter = new MavenFilenameFormatter(filename4);

        Assertions.assertThat(formatter.getArtifactId()).isEqualTo(artifactId);
        Assertions.assertThat(formatter.getVersion()).isEqualTo(versionWithSnapshot);
        Assertions.assertThat(formatter.getTimestamp()).isEqualTo(timestamp);
        Assertions.assertThat(formatter.getBuildNumber()).isEqualTo(buildNumber);
        Assertions.assertThat(formatter.getClassifier()).isEqualTo(classifier);
        Assertions.assertThat(formatter.getExtension()).isEqualTo(extension);
    }

    @Test
    public void formatValidFilenameWithSnapshotClassifierAndSecondExtension() {

        LOG.debug("Test filename to format : " + filename5);

        MavenFilenameFormatter formatter = new MavenFilenameFormatter(filename5);

        Assertions.assertThat(formatter.getArtifactId()).isEqualTo(artifactId);
        Assertions.assertThat(formatter.getVersion()).isEqualTo(versionWithSnapshot);
        Assertions.assertThat(formatter.getTimestamp()).isEqualTo(timestamp);
        Assertions.assertThat(formatter.getBuildNumber()).isEqualTo(buildNumber);
        Assertions.assertThat(formatter.getClassifier()).isEqualTo(classifier);
        Assertions.assertThat(formatter.getExtension()).isEqualTo(extension2);
    }

    @Test(expected = MavenFormatterException.class)
    public void formatInvalidFilenameWithNoArtifactId() {
        LOG.debug("Test filename to format : " + badFilename1);
        new MavenFilenameFormatter(badFilename1).getArtifactId();
    }

    @Test(expected = MavenFormatterException.class)
    public void formatInvalidFilenameWithNoExtension() {
        LOG.debug("Test filename to format : " + badFilename2);
        new MavenFilenameFormatter(badFilename2).getArtifactId();
    }

    @Test(expected = MavenFormatterException.class)
    public void formatInvalidFilenameWithNoVersion() {
        LOG.debug("Test filename to format : " + badFilename3);
        new MavenFilenameFormatter(badFilename3).getArtifactId();
    }

    @Test(expected = MavenFormatterException.class)
    public void formatInvalidFilenameWithInversedTimestampAndBuildnumber() {
        LOG.debug("Test filename to format : " + badFilename4);
        new MavenFilenameFormatter(badFilename4).getArtifactId();
    }
}
