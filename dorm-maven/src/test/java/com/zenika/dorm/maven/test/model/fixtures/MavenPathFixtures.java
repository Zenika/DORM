package com.zenika.dorm.maven.test.model.fixtures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MavenPathFixtures {

    private static final Logger LOG = LoggerFactory.getLogger(MavenPathFixtures.class);

    public static final String GROUPID1 = "org/apache/wicket";
    public static final String GROUPID2 = "commons-io";

    public static final String ARTIFACTID1 = "wicket";
    public static final String ARTIFACTID2 = "commons-io";

    public static final String VERSION_SIMPLE = "1.0";
    public static final String VERSION_TEXT = "1.0-RC";
    public static final String VERSION_SNAPSHOT = "1.0-SNAPSHOT";

    public static final String TIMESTAMP = "20110906.080527";

    public static final String BUILDNUMBER = "100";

    public static final String CLASSIFIER_SIMPLE = "foobar";
    public static final String CLASSIFIER_POINT = "foo.bar";

    public static final String EXTENSION_SIMPLE = "jar";
    public static final String EXTENSION_HASH = "jar.md5";
    public static final String EXTENSION_MULTIPLE_HASH = "jar.asc.md5";
    public static final String EXTENSION_MULTIPLE = "a.b.c.d.e.f";

    public static final String EXTENSION_MULTIPLE_LAST_ELEMENT = "f";
    public static final String EXTENSION_MULTIPLE_HASH_LAST_ELEMENT = "asc.md5";

    public static final String CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE = "foobar.a.b.c.d.e";
    public static final String CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE_HASH = "foobar.jar";

    public static final String CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE = "foo.bar.a.b.c.d.e";
    public static final String CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE_HASH = "foo.bar.jar";

    private MavenPathFixtures() {
    }

    public static String getSimple1() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, null, EXTENSION_SIMPLE);
    }

    public static String getSimple2() {
        return concat(GROUPID2, ARTIFACTID2, VERSION_SIMPLE, null, null, null, EXTENSION_SIMPLE);
    }

    public static String getSimpleWithTextVersion() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_TEXT, null, null, null, EXTENSION_SIMPLE);
    }

    public static String getWithClassifierSimple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_SIMPLE, EXTENSION_SIMPLE);
    }

    public static String getWithClassifierPoint() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_POINT, EXTENSION_SIMPLE);
    }

    public static String getWithExtensionSimple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, null, EXTENSION_SIMPLE);
    }

    public static String getWithExtensionHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, null, EXTENSION_HASH);
    }

    public static String getWithExtensionMultipleHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, null, EXTENSION_MULTIPLE_HASH);
    }

    public static String getWithExtensionMultiple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, null, EXTENSION_MULTIPLE);
    }


    /**
     * Classifier + extension
     */

    public static String getWithClassifierSimpleAndExtensionSimple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_SIMPLE, EXTENSION_SIMPLE);
    }

    public static String getWithClassifierSimpleAndExtensionHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_SIMPLE, EXTENSION_HASH);
    }

    public static String getWithClassifierSimpleAndExtensionMultiple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_SIMPLE, EXTENSION_MULTIPLE);
    }

    public static String getWithClassifierSimpleAndExtensionMultipleHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_SIMPLE, EXTENSION_MULTIPLE_HASH);
    }

    public static String getWithClassifierPointAndExtensionSimple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_POINT, EXTENSION_SIMPLE);
    }

    public static String getWithClassifierPointAndExtensionHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_POINT, EXTENSION_HASH);
    }

    public static String getWithClassifierPointAndExtensionMultiple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_POINT, EXTENSION_MULTIPLE);
    }

    public static String getWithClassifierPointAndExtensionMultipleHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SIMPLE, null, null, CLASSIFIER_POINT, EXTENSION_MULTIPLE_HASH);
    }

    public static String getWithSnapshot() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP, BUILDNUMBER, null, EXTENSION_SIMPLE);
    }

    public static String getWithSnapshotClassifierSimpleAndExtensionSimple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP, BUILDNUMBER, CLASSIFIER_SIMPLE, EXTENSION_SIMPLE);
    }

    public static String getWithSnapshotClassifierPointAndExtensionSimple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP, BUILDNUMBER, CLASSIFIER_POINT, EXTENSION_SIMPLE);
    }

    public static String getWithSnapshotClassifierSimpleAndExtensionHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP, BUILDNUMBER, CLASSIFIER_SIMPLE, EXTENSION_HASH);
    }

    public static String getWithSnapshotClassifierSimpleAndExtensionMultiple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP, BUILDNUMBER, CLASSIFIER_SIMPLE, EXTENSION_MULTIPLE);
    }

    public static String getWithSnapshotClassifierSimpleAndExtensionMultipleHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP, BUILDNUMBER, CLASSIFIER_SIMPLE, EXTENSION_MULTIPLE_HASH);
    }

    public static String getWithSnapshotClassifierPointAndExtensionHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP, BUILDNUMBER, CLASSIFIER_POINT, EXTENSION_HASH);
    }

    public static String getWithSnapshotClassifierPointAndExtensionMultiple() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP, BUILDNUMBER, CLASSIFIER_POINT, EXTENSION_MULTIPLE);
    }

    public static String getWithSnapshotClassifierPointAndExtensionMultipleHash() {
        return concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP, BUILDNUMBER, CLASSIFIER_POINT, EXTENSION_MULTIPLE_HASH);
    }

    private static String concat(String groupdId, String artifactId, String version, String timestamp,
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
}
