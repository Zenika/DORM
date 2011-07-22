package com.zenika.dorm.maven.test.unit;

import com.zenika.dorm.maven.test.helper.MavenFixtures;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractUnitTest {

    protected MavenFixtures fixtures;

    @Before
    public void before() {

        // required by mockito
        MockitoAnnotations.initMocks(this);

        // reset fixtures before every test
        fixtures = new MavenFixtures();
    }
}
