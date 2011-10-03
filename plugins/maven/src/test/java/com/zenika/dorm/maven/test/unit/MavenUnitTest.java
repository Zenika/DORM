package com.zenika.dorm.maven.test.unit;

import com.zenika.dorm.maven.test.fixtures.MavenDependencyFixtures;
import com.zenika.dorm.maven.test.fixtures.MavenFixtures;
import com.zenika.dorm.maven.test.fixtures.MavenMetadataFixtures;
import com.zenika.dorm.maven.test.fixtures.MavenPathFixtures;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class MavenUnitTest {

    protected MavenFixtures fixtures;
    protected MavenPathFixtures pathFixtures;
    protected MavenMetadataFixtures metadataFixtures;
    protected MavenDependencyFixtures dependencyFixtures;

    @Before
    public void before() {

        MockitoAnnotations.initMocks(this);

        pathFixtures = new MavenPathFixtures();
        fixtures = new MavenFixtures(pathFixtures);

        metadataFixtures = new MavenMetadataFixtures();
        dependencyFixtures = new MavenDependencyFixtures(metadataFixtures);
    }
}
