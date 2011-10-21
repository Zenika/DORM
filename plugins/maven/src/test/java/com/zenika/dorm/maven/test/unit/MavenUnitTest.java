package com.zenika.dorm.maven.test.unit;

import com.zenika.dorm.maven.test.fixtures.MavenDependencyFixtures;
import com.zenika.dorm.maven.test.fixtures.MavenFixtures;
import com.zenika.dorm.maven.test.fixtures.MavenHttpPathFixtures;
import com.zenika.dorm.maven.test.fixtures.MavenMetadataFixtures;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class MavenUnitTest {

    protected MavenFixtures fixtures;
    protected MavenHttpPathFixtures httpPathFixtures;
    protected MavenMetadataFixtures metadataFixtures;
    protected MavenDependencyFixtures dependencyFixtures;

    @Before
    public void before() {

        MockitoAnnotations.initMocks(this);

        httpPathFixtures = new MavenHttpPathFixtures();
        fixtures = new MavenFixtures(httpPathFixtures);

        metadataFixtures = new MavenMetadataFixtures();
        dependencyFixtures = new MavenDependencyFixtures(metadataFixtures);
    }
}
