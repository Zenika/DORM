package com.zenika.dorm.maven.test.fixtures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenPathFixtures {

    private static final Logger LOG = LoggerFactory.getLogger(MavenPathFixtures.class);

    private String simple1;
    private String simple2;
    private String simpleWithTextVersion;
    private String withClassifierSimple;
    private String withClassifierPoint;
    private String withExtensionSimple;
    private String withExtensionHash;
    private String withExtensionMultipleHash;
    private String withExtensionMultiple;


    /**
     * Classifier + extension
     */

    public String withClassifierSimpleAndExtensionSimple;
    public String withClassifierSimpleAndExtensionHash;
    public String withClassifierSimpleAndExtensionMultiple;
    public String withClassifierSimpleAndExtensionMultipleHash;
    public String withClassifierPointAndExtensionSimple;
    public String withClassifierPointAndExtensionHash;
    public String withClassifierPointAndExtensionMultiple;
    public String withClassifierPointAndExtensionMultipleHash;
    public String withSnapshot;
    public String withSnapshotClassifierSimpleAndExtensionSimple;
    public String withSnapshotClassifierPointAndExtensionSimple;
    public String withSnapshotClassifierSimpleAndExtensionHash;
    public String withSnapshotClassifierSimpleAndExtensionMultiple;
    public String withSnapshotClassifierSimpleAndExtensionMultipleHash;
    public String withSnapshotClassifierPointAndExtensionHash;
    public String withSnapshotClassifierPointAndExtensionMultiple;
    public String withSnapshotClassifierPointAndExtensionMultipleHash;

    public MavenPathFixtures() {

        simple1 = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1,
                MavenConstantFixtures.VERSION_SIMPLE, null, null, null, MavenConstantFixtures.EXTENSION_SIMPLE);

        simple2 = concat(MavenConstantFixtures.GROUPID2, MavenConstantFixtures.ARTIFACTID2, MavenConstantFixtures.VERSION_SIMPLE,
                null, null, null, MavenConstantFixtures.EXTENSION_SIMPLE);

        simpleWithTextVersion = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_TEXT,
                null, null, null, MavenConstantFixtures.EXTENSION_SIMPLE);

        withClassifierSimple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE,
                null, null, MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_SIMPLE);

        withClassifierPoint = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE,
                null, null, MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_SIMPLE);

        withExtensionSimple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE,
                null, null, null, MavenConstantFixtures.EXTENSION_SIMPLE);

        withExtensionHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE,
                null, null, null, MavenConstantFixtures.EXTENSION_HASH);

        withExtensionMultipleHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE,
                null, null, null, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH);

        withExtensionMultiple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE,
                null, null, null, MavenConstantFixtures.EXTENSION_MULTIPLE);

        withClassifierSimpleAndExtensionSimple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE, null, null,
                MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_SIMPLE);

        withClassifierSimpleAndExtensionHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE, null, null,
                MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_HASH);

        withClassifierSimpleAndExtensionMultiple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE, null, null,
                MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_MULTIPLE);

        withClassifierSimpleAndExtensionMultipleHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE, null, null,
                MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH);

        withClassifierPointAndExtensionSimple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE, null, null,
                MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_SIMPLE);

        withClassifierPointAndExtensionHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE, null, null,
                MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_HASH);

        withClassifierPointAndExtensionMultiple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE, null, null,
                MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_MULTIPLE);

        withClassifierPointAndExtensionMultipleHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SIMPLE, null, null,
                MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH);

        withSnapshot = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SNAPSHOT, MavenConstantFixtures.TIMESTAMP,
                MavenConstantFixtures.BUILDNUMBER, null, MavenConstantFixtures.EXTENSION_SIMPLE);

        withSnapshotClassifierSimpleAndExtensionSimple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SNAPSHOT,
                MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER, MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_SIMPLE);

        withSnapshotClassifierPointAndExtensionSimple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SNAPSHOT,
                MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER, MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_SIMPLE);

        withSnapshotClassifierSimpleAndExtensionHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SNAPSHOT,
                MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER, MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_HASH);

        withSnapshotClassifierSimpleAndExtensionMultiple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SNAPSHOT,
                MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER, MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_MULTIPLE);

        withSnapshotClassifierSimpleAndExtensionMultipleHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SNAPSHOT,
                MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER, MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH);

        withSnapshotClassifierPointAndExtensionHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SNAPSHOT,
                MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER, MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_HASH);

        withSnapshotClassifierPointAndExtensionMultiple = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SNAPSHOT,
                MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER, MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_MULTIPLE);

        withSnapshotClassifierPointAndExtensionMultipleHash = concat(MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1, MavenConstantFixtures.VERSION_SNAPSHOT,
                MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER, MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH);
    }

    private String concat(String groupdId, String artifactId, String version, String timestamp,
                          String buildNumber, String classifier, String extension) {

        StringBuilder builder = new StringBuilder(100)
                .append(groupdId).append("/")
                .append(artifactId).append("/")
                .append(version).append("/")
                .append(artifactId).append("-")
                .append(version.replace("-SNAPSHOT", ""));

        if (null != timestamp && null != buildNumber) {
            builder.append("-").append(timestamp)
                    .append("-").append(buildNumber);
        }

        if (null != classifier) {
            builder.append("-").append(classifier);
        }

        builder.append(".").append(extension);

        String uri = builder.toString();
        LOG.debug("Generated URI for test : " + uri);

        return uri;
    }

    public String getSimple1() {
        return simple1;
    }

    public String getSimple2() {
        return simple2;
    }

    public String getSimpleWithTextVersion() {
        return simpleWithTextVersion;
    }

    public String getWithClassifierSimple() {
        return withClassifierSimple;
    }

    public String getWithClassifierPoint() {
        return withClassifierPoint;
    }

    public String getWithExtensionSimple() {
        return withExtensionSimple;
    }

    public String getWithExtensionHash() {
        return withExtensionHash;
    }

    public String getWithExtensionMultipleHash() {
        return withExtensionMultipleHash;
    }

    public String getWithExtensionMultiple() {
        return withExtensionMultiple;
    }

    public String getWithClassifierSimpleAndExtensionSimple() {
        return withClassifierSimpleAndExtensionSimple;
    }

    public String getWithClassifierSimpleAndExtensionHash() {
        return withClassifierSimpleAndExtensionHash;
    }

    public String getWithClassifierSimpleAndExtensionMultiple() {
        return withClassifierSimpleAndExtensionMultiple;
    }

    public String getWithClassifierSimpleAndExtensionMultipleHash() {
        return withClassifierSimpleAndExtensionMultipleHash;
    }

    public String getWithClassifierPointAndExtensionSimple() {
        return withClassifierPointAndExtensionSimple;
    }

    public String getWithClassifierPointAndExtensionHash() {
        return withClassifierPointAndExtensionHash;
    }

    public String getWithClassifierPointAndExtensionMultiple() {
        return withClassifierPointAndExtensionMultiple;
    }

    public String getWithClassifierPointAndExtensionMultipleHash() {
        return withClassifierPointAndExtensionMultipleHash;
    }

    public String getWithSnapshot() {
        return withSnapshot;
    }

    public String getWithSnapshotClassifierSimpleAndExtensionSimple() {
        return withSnapshotClassifierSimpleAndExtensionSimple;
    }

    public String getWithSnapshotClassifierPointAndExtensionSimple() {
        return withSnapshotClassifierPointAndExtensionSimple;
    }

    public String getWithSnapshotClassifierSimpleAndExtensionHash() {
        return withSnapshotClassifierSimpleAndExtensionHash;
    }

    public String getWithSnapshotClassifierSimpleAndExtensionMultiple() {
        return withSnapshotClassifierSimpleAndExtensionMultiple;
    }

    public String getWithSnapshotClassifierSimpleAndExtensionMultipleHash() {
        return withSnapshotClassifierSimpleAndExtensionMultipleHash;
    }

    public String getWithSnapshotClassifierPointAndExtensionHash() {
        return withSnapshotClassifierPointAndExtensionHash;
    }

    public String getWithSnapshotClassifierPointAndExtensionMultiple() {
        return withSnapshotClassifierPointAndExtensionMultiple;
    }

    public String getWithSnapshotClassifierPointAndExtensionMultipleHash() {
        return withSnapshotClassifierPointAndExtensionMultipleHash;
    }
}
