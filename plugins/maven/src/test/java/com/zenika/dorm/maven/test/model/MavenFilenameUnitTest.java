package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.model.MavenFilename;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.test.model.fixtures.MavenPathFixtures;
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

        MavenFilename mavenFilename = new MavenUri(MavenPathFixtures.getWithClassifierSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenPathFixtures.CLASSIFIER_SIMPLE, MavenPathFixtures.EXTENSION_SIMPLE);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithClassifierPoint()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenPathFixtures.CLASSIFIER_POINT, MavenPathFixtures.EXTENSION_SIMPLE);
    }

    @Test
    public void testMavenFilenameWithExtension() throws Exception {

        MavenFilename mavenFilename = new MavenUri(MavenPathFixtures.getWithExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenPathFixtures.EXTENSION_SIMPLE);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithExtensionHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenPathFixtures.EXTENSION_HASH);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenPathFixtures.EXTENSION_MULTIPLE);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, null, MavenPathFixtures.EXTENSION_MULTIPLE_HASH);
    }

    @Test
    public void testMavenFilenameWithSnapshot() throws Exception {

        MavenFilename mavenFilename = new MavenUri(MavenPathFixtures.getWithSnapshot()).getFilename();
        compareFilenameWith(mavenFilename, MavenPathFixtures.TIMESTAMP, MavenPathFixtures.BUILDNUMBER,
                null, MavenPathFixtures.EXTENSION_SIMPLE);
    }

    @Test
    public void testFilenameUriWithClassifierAndSimpleExtension() throws Exception {

        // normal
        MavenFilename mavenFilename = new MavenUri(MavenPathFixtures.getWithClassifierSimpleAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenPathFixtures.CLASSIFIER_SIMPLE, MavenPathFixtures.EXTENSION_SIMPLE);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithClassifierPointAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenPathFixtures.CLASSIFIER_POINT, MavenPathFixtures.EXTENSION_SIMPLE);

        // snapshot
        mavenFilename = new MavenUri(MavenPathFixtures.getWithSnapshotClassifierSimpleAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, MavenPathFixtures.TIMESTAMP, MavenPathFixtures.BUILDNUMBER,
                MavenPathFixtures.CLASSIFIER_SIMPLE, MavenPathFixtures.EXTENSION_SIMPLE);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithSnapshotClassifierPointAndExtensionSimple()).getFilename();
        compareFilenameWith(mavenFilename, MavenPathFixtures.TIMESTAMP, MavenPathFixtures.BUILDNUMBER,
                MavenPathFixtures.CLASSIFIER_POINT, MavenPathFixtures.EXTENSION_SIMPLE);
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
        MavenFilename mavenFilename = new MavenUri(MavenPathFixtures.getWithClassifierSimpleAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenPathFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE, MavenPathFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithClassifierSimpleAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenPathFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE_HASH, MavenPathFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);

        // classifier point
        mavenFilename = new MavenUri(MavenPathFixtures.getWithClassifierPointAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenPathFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE, MavenPathFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithClassifierPointAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, null, null, MavenPathFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE_HASH, MavenPathFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);

        // snapshot
        // classifier simple
        mavenFilename = new MavenUri(MavenPathFixtures.getWithSnapshotClassifierSimpleAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, MavenPathFixtures.TIMESTAMP, MavenPathFixtures.BUILDNUMBER,
                MavenPathFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE, MavenPathFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithSnapshotClassifierSimpleAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, MavenPathFixtures.TIMESTAMP, MavenPathFixtures.BUILDNUMBER,
                MavenPathFixtures.CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE_HASH, MavenPathFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);

        // classifier point
        mavenFilename = new MavenUri(MavenPathFixtures.getWithSnapshotClassifierPointAndExtensionMultiple()).getFilename();
        compareFilenameWith(mavenFilename, MavenPathFixtures.TIMESTAMP, MavenPathFixtures.BUILDNUMBER,
                MavenPathFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE, MavenPathFixtures.EXTENSION_MULTIPLE_LAST_ELEMENT);

        mavenFilename = new MavenUri(MavenPathFixtures.getWithSnapshotClassifierPointAndExtensionMultipleHash()).getFilename();
        compareFilenameWith(mavenFilename, MavenPathFixtures.TIMESTAMP, MavenPathFixtures.BUILDNUMBER,
                MavenPathFixtures.CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE_HASH, MavenPathFixtures.EXTENSION_MULTIPLE_HASH_LAST_ELEMENT);
    }

    private void compareFilenameWith(MavenFilename filename, String timestamp, String buildnumber,
                                     String classifier, String extension) {

        Assertions.assertThat(filename.getTimestamp()).as("timestamp").isEqualTo(timestamp);
        Assertions.assertThat(filename.getBuildNumber()).as("buildnumber").isEqualTo(buildnumber);
        Assertions.assertThat(filename.getClassifier()).as("classifier").isEqualTo(classifier);
        Assertions.assertThat(filename.getExtension()).as("extension").isEqualTo(extension);
    }
}
