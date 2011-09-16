package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.test.fixtures.MavenConstantFixtures;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenUriUnitTest extends AbstractUnitTest {

    @Test
    public void testMavenUriSimple() throws Exception {

        MavenUri mavenUri = new MavenUri(pathFixtures.getSimple1());
        compareUriWith(mavenUri, MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1,
                MavenConstantFixtures.VERSION_SIMPLE, false);

        mavenUri = new MavenUri(pathFixtures.getSimple2());
        compareUriWith(mavenUri, MavenConstantFixtures.GROUPID2, MavenConstantFixtures.ARTIFACTID2,
                MavenConstantFixtures.VERSION_SIMPLE, false);
    }

    @Test
    public void testMavenUriWithSpecialVersion() throws Exception {

        // text version "-xxx"
        MavenUri mavenUri = new MavenUri(pathFixtures.getSimpleWithTextVersion());
        compareUriWith(mavenUri, MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1,
                MavenConstantFixtures.VERSION_TEXT, false);

        // snapshot "-SNAPSHOT"
        mavenUri = new MavenUri(pathFixtures.getWithSnapshot());
        compareUriWith(mavenUri, MavenConstantFixtures.GROUPID1, MavenConstantFixtures.ARTIFACTID1,
                MavenConstantFixtures.VERSION_SNAPSHOT, true);
    }

    private void compareUriWith(MavenUri uri, String groupId, String artifactId, String version,
                                boolean snapshot) {

        Assertions.assertThat(uri.getGroupId()).as("groupid").isEqualTo(groupId);
        Assertions.assertThat(uri.getArtifactId()).as("artifactid").isEqualTo(artifactId);
        Assertions.assertThat(uri.getVersion()).as("version").isEqualTo(version);
        Assertions.assertThat(uri.isSnapshot()).as("is snapshot").isEqualTo(snapshot);
    }
}
