package com.zenika.dorm.test.ws.resource;

import com.zenika.dorm.maven.client.MavenClientConfig;
import com.zenika.dorm.maven.client.MavenClientService;
import com.zenika.dorm.maven.test.helper.MavenFixtures;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class MavenResourceIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenResourceIntegrationTest.class);

    private static final String MAVEN_URL = BASE_URL + "/maven";

    private MavenFixtures fixtures;
    private MavenClientService mavenClient;

    @Override
    public void before() {
        super.before();
        fixtures = new MavenFixtures();

        mavenClient = new MavenClientService(new MavenClientConfig.Builder("tmp/maven-client/test-repo", "test",
                MAVEN_URL).build());
    }

    @Override
    protected String getResourceUrl() {
        return MAVEN_URL;
    }

    @Test
    public void pushValidArtifact() {
        Dependency dependency = fixtures.getDependencyWithResource();
        mavenClient.deploy(dependency);
    }
}
