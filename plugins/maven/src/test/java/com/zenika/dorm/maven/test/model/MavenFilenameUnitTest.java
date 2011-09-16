package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.model.MavenFilename;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.test.fixtures.MavenConstantFixtures;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFilenameUnitTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFilenameUnitTest.class);

    @Test
    public void testMavenFilenameWithClassifier() throws Exception {

        MavenFilename mavenFilename = new MavenUri(pathFixtures.getWithClassifierSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_SIMPLE);

        mavenFilename = new MavenUri(pathFixtures.getWithClassifierPoint()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_SIMPLE);
    }

    @Test
    public void testMavenFilenameWithExtension() throws Exception {

        MavenFilename mavenFilename = new MavenUri(pathFixtures.getWithExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenConstantFixtures.EXTENSION_SIMPLE);

        mavenFilename = new MavenUri(pathFixtures.getWithExtensionHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenConstantFixtures.EXTENSION_HASH);

        mavenFilename = new MavenUri(pathFixtures.getWithExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenConstantFixtures.EXTENSION_MULTIPLE);

        mavenFilename = new MavenUri(pathFixtures.getWithExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH);
    }

    @Test
    public void testMavenFilenameWithSnapshot() throws Exception {

        MavenFilename mavenFilename = new MavenUri(pathFixtures.getWithSnapshot()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                null, MavenConstantFixtures.EXTENSION_SIMPLE);
    }

    @Test
    public void testFilenameUriWithClassifierAndSimpleExtension() throws Exception {

        // normal
        MavenFilename mavenFilename = new MavenUri(pathFixtures.getWithClassifierSimpleAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_SIMPLE);

        mavenFilename = new MavenUri(pathFixtures.getWithClassifierPointAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_SIMPLE);

        // snapshot
        mavenFilename = new MavenUri(pathFixtures.getWithSnapshotClassifierSimpleAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_SIMPLE, MavenConstantFixtures.EXTENSION_SIMPLE);

        mavenFilename = new MavenUri(pathFixtures.getWithSnapshotClassifierPointAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_POINT, MavenConstantFixtures.EXTENSION_SIMPLE);
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
        MavenFilename mavenFilename = new MavenUri(pathFixtures.getWithClassifierSimpleAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE,
                MavenConstantFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(pathFixtures.getWithClassifierSimpleAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE_HASH,
                MavenConstantFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);

        // classifier point
        mavenFilename = new MavenUri(pathFixtures.getWithClassifierPointAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE,
                MavenConstantFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(pathFixtures.getWithClassifierPointAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenConstantFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE_HASH,
                MavenConstantFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);

        // snapshot
        // classifier simple
        mavenFilename = new MavenUri(pathFixtures.getWithSnapshotClassifierSimpleAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE, MavenConstantFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(pathFixtures.getWithSnapshotClassifierSimpleAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE_HASH, MavenConstantFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);

        // classifier point
        mavenFilename = new MavenUri(pathFixtures.getWithSnapshotClassifierPointAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, MavenConstantFixtures.TIMESTAMP, MavenConstantFixtures.BUILDNUMBER,
                MavenConstantFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE, MavenConstantFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(pathFixtures.getWithSnapshotClassifierPointAndExtensionMultipleHash()).getFilename();
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
