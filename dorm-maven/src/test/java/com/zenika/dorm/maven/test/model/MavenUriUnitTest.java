package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.test.model.fixtures.MavenPathFixtures;
import com.zenika.dorm.maven.test.model.fixtures.MavenUriFixture;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class MavenUriUnitTest extends AbstractUnitTest {

    private MavenUriFixture uriFixture1;
    private MavenUriFixture uriFixture2;
    private MavenUriFixture uriFixture3;
    private MavenUriFixture uriFixture4;
    private MavenUriFixture uriFixture5;
    private MavenUriFixture uriFixture6;

    @Before
    public void setUp() throws Exception {
        uriFixture1 = new MavenUriFixture(MavenPathFixtures.URI1);
        uriFixture2 = new MavenUriFixture(MavenPathFixtures.URI2);
        uriFixture3 = new MavenUriFixture(MavenPathFixtures.URI3);
        uriFixture4 = new MavenUriFixture(MavenPathFixtures.URI4);
        uriFixture5 = new MavenUriFixture(MavenPathFixtures.URI_SNAPSHOT1);
        uriFixture6 = new MavenUriFixture(MavenPathFixtures.URI_SNAPSHOT1);
    }

    @Test
    public void testValidMavenUri() throws Exception {
        uriFixture1.compare();
        uriFixture2.compare();
        uriFixture3.compare();
        uriFixture4.compare();
    }

    @Test
    public void testValidMavenSnapshotUri() throws Exception {
        uriFixture5.compare();
        uriFixture6.compare();
    }
}
