package com.zenika.dorm.maven.test.unit;

import com.zenika.dorm.maven.test.fixtures.MavenFixtures;
import com.zenika.dorm.maven.test.fixtures.MavenPathFixtures;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractUnitTest {

    protected MavenFixtures fixtures;
    protected MavenPathFixtures pathFixtures;

    @Before
    public void before() {

        MockitoAnnotations.initMocks(this);

        pathFixtures = new MavenPathFixtures();
        fixtures = new MavenFixtures(pathFixtures);
    }
}
