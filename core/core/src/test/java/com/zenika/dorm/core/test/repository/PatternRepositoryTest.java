package com.zenika.dorm.core.test.repository;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.repository.DormRepositoryConfiguration;
import com.zenika.dorm.core.repository.impl.DormRepositoryPatternAssociate;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import com.zenika.dorm.core.test.model.DormMetadataTest;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.fest.assertions.Assertions.*;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class PatternRepositoryTest {

    private static final String BASE_PATH_REPOSITORY_TEST = "/home/erouan/repository_tmp";
    private static final String TEST_JAR = "/com/zenika/dorm/core/test/resources/zenika_test.jar";

    private File testJarRepo;

    @Before
    public void setUp() {
        try {
            File basePathTest = new File(BASE_PATH_REPOSITORY_TEST + "/group_test/zenika_test/1.2.0/");
            basePathTest.mkdirs();
            testJarRepo = new File(basePathTest, "zenika_test-1.2.0.jar");
            File testJar = new File(getClass().getResource(TEST_JAR).toURI());
            FileUtils.copyFile(testJar, testJarRepo);
        } catch (URISyntaxException e) {
            throw new RuntimeException("URI syntax exception", e);
        } catch (IOException e) {
            throw new RuntimeException("IO error", e);
        }
    }

    @Test
    public void getTest() {
        DormMetadataTest dormMetadata = new DormMetadataTest("1.2.0", "zenika_test");
        dormMetadata.setGroupId("group_test");
        DormResource expectedResource = new DefaultDormResource("zenika_test", "jar", testJarRepo);
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ExtensionFactoryServiceLoader.class);
                bind(DormRepositoryConfiguration.class).toInstance(getConfiguration());
            }
        });
        DormRepositoryPatternAssociate repositoryPatternAssociate = injector.getInstance(DormRepositoryPatternAssociate.class);
        DormResource resultResource = repositoryPatternAssociate.get(dormMetadata);
        assertThat(resultResource).isEqualTo(expectedResource);
    }

    private DormRepositoryConfiguration getConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        URL url = getClass().getResource("/com/zenika/dorm/core/test/resources/test_repository_configuration.json");
        try {
            return mapper.readValue(url, DormRepositoryConfiguration.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to retrieve the configuration", e);
        }
    }

    @After
    public void tearDown() {
        try {
            FileUtils.deleteDirectory(new File(BASE_PATH_REPOSITORY_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Unable to delete this directory: " + BASE_PATH_REPOSITORY_TEST, e);
        }
    }
}
