package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.test.model.fixtures.MavenFilenameFixture;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class MavenFilenameUnitTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFilenameUnitTest.class);

    private static final String BASE = "org/apache/wicket/wicket/1.4.9/wicket-1.4.9";
    private static final String BASE_SNAPSHOT = "org/apache/wicket/wicket/1.4.9-SNAPSHOT/wicket-1.4.9";

    private String uri1 = BASE + "-javadoc.jar.asc.md5";
    private String uri2 = BASE + ".jar.asc.md5";
    private String uri3 = BASE + "-foobar.jar";
    private String uri4 = BASE + "-foobar.jar.sha1";

    private String uriSnapshot1 = BASE_SNAPSHOT + "-20110906.080527-1.jar.asc.sha1";
    private String uriSnapshot2 = BASE_SNAPSHOT + "-20110906.080527-1-foobar.jar.sha1";
    private String uriSnapshot3 = BASE_SNAPSHOT + "-20110906.080527-1-foo.bar.jar.sha1";

    private MavenFilenameFixture mavenFilenameFixture1;
    private MavenFilenameFixture mavenFilenameFixture2;
    private MavenFilenameFixture mavenFilenameFixture3;
    private MavenFilenameFixture mavenFilenameFixture4;
    private MavenFilenameFixture mavenFilenameFixtureSnapshot1;
    private MavenFilenameFixture mavenFilenameFixtureSnapshot2;
    private MavenFilenameFixture mavenFilenameFixtureSnapshot3;

    @Before
    public void setUp() throws Exception {

        mavenFilenameFixture1 = new MavenFilenameFixture(uri1)
                .classifier("javadoc.jar")
                .extension("asc.md5");

        mavenFilenameFixture2 = new MavenFilenameFixture(uri2)
                .extension("jar.asc.md5");

        mavenFilenameFixture3 = new MavenFilenameFixture(uri3)
                .classifier("foobar")
                .extension("jar");

        mavenFilenameFixture4 = new MavenFilenameFixture(uri4)
                .classifier("foobar")
                .extension("jar.sha1");

        mavenFilenameFixtureSnapshot1 = new MavenFilenameFixture(uriSnapshot1)
                .timestamp("20110906.080527")
                .buildNumber("1")
                .extension("jar.asc.sha1");

        mavenFilenameFixtureSnapshot2 = new MavenFilenameFixture(uriSnapshot2)
                .timestamp("20110906.080527")
                .buildNumber("1")
                .classifier("foobar")
                .extension("jar.sha1");

        mavenFilenameFixtureSnapshot3 = new MavenFilenameFixture(uriSnapshot3)
                .timestamp("20110906.080527")
                .buildNumber("1")
                .classifier("foo.bar")
                .extension("jar.sha1");
    }

    @Test
    public void extractMetadataFromValidFilenames() throws Exception {
        mavenFilenameFixture1.compare();
        mavenFilenameFixture2.compare();
        mavenFilenameFixture3.compare();
        mavenFilenameFixture4.compare();
    }

    @Test
    public void extractMetadataFromValidSnapshotFilenames() throws Exception {
        mavenFilenameFixtureSnapshot1.compare();
        mavenFilenameFixtureSnapshot2.compare();
        mavenFilenameFixtureSnapshot3.compare();
    }
}
