package com.zenika.dorm.importer.test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.zenika.dorm.core.guice.module.DormCoreModule;
import com.zenika.dorm.core.guice.module.DormRepositoryConfigurationModule;
import com.zenika.dorm.core.guice.module.dao.DormCoreNeo4jModule;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.processor.extension.RequestAnalyser;
import com.zenika.dorm.core.repository.impl.DefaultDormRepository;
import com.zenika.dorm.importer.ImportConfiguration;
import com.zenika.dorm.importer.MavenRepositoryImporter;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.service.MavenProxyService;
import com.zenika.dorm.maven.service.MavenProxyServiceHttp;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.internal.stubbing.answers.ClonesArguments;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenImporterTest {

    private static final String REPOSITORY_PATH_TEST = "tmp/repo-test";
    private static final int NUMBER_OF_GENERATED_ARTIFACT = 1;

    private static MavenRepositoryGenerator generator;
    private static List<MavenRepositoryArtifact> mavenRepositoryArtifacts;

    private MavenRepositoryImporter importer;
    private MavenProcessor mavenProcessor;

    @BeforeClass
    public static void setUpClass() {
        mavenRepositoryArtifacts = new ArrayList<MavenRepositoryArtifact>();
        generator = new MavenRepositoryGenerator(REPOSITORY_PATH_TEST, 1);
        generator.addGeneratorListener(new MavenRepositoryGeneratorListener() {
            @Override
            public void artifactGenerated(MavenRepositoryArtifact mavenRepositoryArtifact) {
                MavenImporterTest.mavenRepositoryArtifacts.add(mavenRepositoryArtifact);
            }
        });
        generator.generate();
    }

    @Before
    public void setUp() {
        mavenProcessor = mock(MavenProcessor.class);
        final ImportConfiguration configuration = new ImportConfiguration();
        configuration.setBasePath(REPOSITORY_PATH_TEST);
        configuration.setRepositoryName("Repository test");
        doAnswer(new ClonesArguments()).when(mavenProcessor).push(any(DormWebServiceRequest.class));
        Injector injector = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(MavenProcessor.class).toInstance(mavenProcessor);
                        bind(MavenProxyService.class).to(MavenProxyServiceHttp.class);
                        bind(new TypeLiteral<Set<RequestAnalyser>>() {}).toInstance(new HashSet<RequestAnalyser>());
                        bind(ImportConfiguration.class).toInstance(configuration);
                    }
                },
                new DormCoreModule(),
                new DormCoreNeo4jModule(),
                new DormRepositoryConfigurationModule(DefaultDormRepository.class)
                );
        importer = injector.getInstance(MavenRepositoryImporter.class);
    }

    @Test
    public void test() {
        importer.importProcess();
        verify(mavenProcessor, times(6 * NUMBER_OF_GENERATED_ARTIFACT)).push(argThat(new IsExpectedWebServiceRequest()));
    }

    @AfterClass
    public static void afterClass() {
        generator.deleteRepository();
    }

    private class IsExpectedWebServiceRequest extends ArgumentMatcher<DormWebServiceRequest> {

        @Override
        public boolean matches(Object argument) {
            DormWebServiceRequest request = (DormWebServiceRequest) argument;
            if (!request.getOrigin().equals(MavenMetadata.EXTENSION_NAME)) {
                return false;
            }
            File file = request.getFile();
            for (MavenRepositoryArtifact mavenRepositoryArtifact : mavenRepositoryArtifacts) {
                if (file.equals(mavenRepositoryArtifact.getJarFile()) ||
                        file.equals(mavenRepositoryArtifact.getJarMd5File()) ||
                        file.equals(mavenRepositoryArtifact.getJarSha1File()) ||
                        file.equals(mavenRepositoryArtifact.getPomFile()) ||
                        file.equals(mavenRepositoryArtifact.getPomMd5File()) ||
                        file.equals(mavenRepositoryArtifact.getPomSha1File())) {
                    return true;
                }
            }
            return false;
        }
    }
}
