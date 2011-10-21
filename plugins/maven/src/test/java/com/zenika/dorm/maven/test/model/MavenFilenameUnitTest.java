package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.model.MavenFilename;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.test.fixtures.MavenConstantFixtures;
import com.zenika.dorm.maven.test.unit.MavenUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFilenameUnitTest extends MavenUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFilenameUnitTest.class);

    @Test
    public void testMavenFilenameWithClassifier() throws Exception {

        MavenFilename mavenFilename = new MavenUri(httpPathFixtures.getWithClassifierSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_SIMPLE_JAR);

        mavenFilename = new MavenUri(httpPathFixtures.getWithClassifierPoint()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_SIMPLE_JAR);
    }

    @Test
    public void testMavenFilenameWithExtension() throws Exception {

        MavenFilename mavenFilename = new MavenUri(httpPathFixtures.getWithExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenConstantFixtures.EXTENSION_SIMPLE_JAR);

        mavenFilename = new MavenUri(httpPathFixtures.getWithExtensionHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenConstantFixtures.EXTENSION_HASH_JAR_MD5);

        mavenFilename = new MavenUri(httpPathFixtures.getWithExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenConstantFixtures.EXTENSION_MULTIPLE);

        mavenFilename = new MavenUri(httpPathFixtures.getWithExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH);
    }

    @Test
    public void testMavenFilenameWithSnapshot() throws Exception {

        MavenFilename mavenFilename = new MavenUri(httpPathFixtures.getWithSnapshot()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                null, MavenConstantFixtures.EXTENSION_SIMPLE_JAR);
    }

    @Test
    public void testFilenameUriWithClassifierAndSimpleExtension() throws Exception {

        // normal
        MavenFilename mavenFilename = new MavenUri(httpPathFixtures.getWithClassifierSimpleAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_SIMPLE_JAR);

        mavenFilename = new MavenUri(httpPathFixtures.getWithClassifierPointAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_SIMPLE_JAR);

        // snapshot
        mavenFilename = new MavenUri(httpPathFixtures.getWithSnapshotClassifierSimpleAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_SIMPLE_JAR);

        mavenFilename = new MavenUri(httpPathFixtures.getWithSnapshotClassifierPointAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_SIMPLE_JAR);
    }

    /**
     * Incorrect filename formatting excpected
     *
     * @throws Exception
     */
    @Test
    public void testFilenameUriWithClassifierAndMultipleExtension() throws Exception {

        // normal
        // classifier simple
        MavenFilename mavenFilename = new MavenUri(httpPathFixtures.getWithClassifierSimpleAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE,
                MavenConstantFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(httpPathFixtures.getWithClassifierSimpleAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE_HASH,
                MavenConstantFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);

        // classifier point
        mavenFilename = new MavenUri(httpPathFixtures.getWithClassifierPointAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE,
                MavenConstantFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(httpPathFixtures.getWithClassifierPointAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE_HASH,
                MavenConstantFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);

        // snapshot
        // classifier simple
        mavenFilename = new MavenUri(httpPathFixtures.getWithSnapshotClassifierSimpleAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE, MavenConstantFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(httpPathFixtures.getWithSnapshotClassifierSimpleAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE_HASH, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);

        // classifier point
        mavenFilename = new MavenUri(httpPathFixtures.getWithSnapshotClassifierPointAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE, MavenConstantFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(httpPathFixtures.getWithSnapshotClassifierPointAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE_HASH, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);
    }

    private void compareFilenameWith(MavenFilename filename, String timestamp, String buildnumber,
                                     String classifier, String extension) {

        Assertions.assertThat(filename.getTimestamp()).as("timestamp").isEqualTo(timestamp);
        Assertions.assertThat(filename.getBuildNumber()).as("buildnumber").isEqualTo(buildnumber);
        Assertions.assertThat(filename.getClassifier()).as("classifier").isEqualTo(classifier);
        Assertions.assertThat(filename.getExtension()).as("extension").isEqualTo(extension);
    }
}
