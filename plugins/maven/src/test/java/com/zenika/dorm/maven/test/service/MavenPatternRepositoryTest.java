package com.zenika.dorm.maven.test.service;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.repository.DormRepositoryConfiguration;
import com.zenika.dorm.core.repository.impl.DormRepositoryPatternAssociate;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenMetadata;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenPatternRepositoryTest {

    private static final String BASE_PATH_REPOSITORY_TEST = "/home/erouan/repository_maven";
    private static final String TEST_JAR = "/com/zenika/dorm/core/test/resources/zenika_test.jar";

    private File testJarRepo;

    @Before
    public void setUp() {
        try {
            File basePathTest = new File(BASE_PATH_REPOSITORY_TEST + "/com/zenika/zenika/1.2.0/");
            File basePathTest1 = new File(BASE_PATH_REPOSITORY_TEST + "/com/zenika/zenika_test/1.2.0/");
            basePathTest.mkdirs();
            basePathTest1.mkdirs();
            testJarRepo = new File(basePathTest1, "zenika_test-1.2.0-bin.jar");
            File testJarRepo2 = new File(basePathTest, "zenika-1.2.0-bin.jar");
            File testJar = new File(getClass().getResource(TEST_JAR).toURI());
            FileUtils.copyFile(testJar, testJarRepo);
            FileUtils.copyFile(testJar, testJarRepo2);
        } catch (URISyntaxException e) {
            throw new RuntimeException("URI syntax exception", e);
        } catch (IOException e) {
            throw new RuntimeException("IO error", e);
        }
    }

    @Test
    public void getTest() {
        MavenBuildInfo mavenBuildInfo = new MavenBuildInfo("jar", "bin", null, null);
        MavenMetadata mavenMetadata = new MavenMetadata(null, "com.zenika", "zenika_test", "1.2.0", mavenBuildInfo);
        DormResource expectedResource = new DefaultDormResource(mavenMetadata.getName(), "jar", testJarRepo);
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ExtensionFactoryServiceLoader.class);
                bind(DormRepositoryConfiguration.class).toInstance(getConfiguration());
            }
        });
        DormRepositoryPatternAssociate repositoryPatternAssociate = injector.getInstance(DormRepositoryPatternAssociate.class);
        DormResource resultResource = repositoryPatternAssociate.get(mavenMetadata, null);
        assertThat(resultResource).isEqualTo(expectedResource);
    }

    @After
    public void tearDown() {
        try {
            FileUtils.deleteDirectory(new File(BASE_PATH_REPOSITORY_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Unable to delete this directory: " + BASE_PATH_REPOSITORY_TEST, e);
        }
    }

    private DormRepositoryConfiguration getConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        URL url = getClass().getResource("/com/zenika/dorm/maven/configuration/maven_repository_configuration.json");
        try {
            return mapper.readValue(url, DormRepositoryConfiguration.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to retrieve the configuration", e);
        }
    }
}
