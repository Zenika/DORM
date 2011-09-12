package com.zenika.dorm.test.ws.resource;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.maven.client.MavenClientConfig;
import com.zenika.dorm.maven.client.MavenClientService;
import com.zenika.dorm.maven.test.helper.MavenFixtures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenTmpResourceTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenTmpResourceTest.class);

    private static final String MAVEN_URL = AbstractIntegrationTest.BASE_URL + "/maven";

    private static MavenFixtures fixtures;
    private static MavenClientService mavenClient;


    public static void before() {
        fixtures = new MavenFixtures();
        mavenClient = new MavenClientService(new MavenClientConfig.Builder("tmp/maven-client/test-repo", "test",
                MAVEN_URL).build());
    }

    public static void main(String[] args) {
        before();
        Dependency dependency = fixtures.getDependencyWithResource();// getSnapshotDependencyWithResource();
        mavenClient.deploy(dependency);
    }
}
