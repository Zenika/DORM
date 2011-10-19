package com.zenika.dorm.maven.test.service;

import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.maven.pom.MavenPomReader;
import com.zenika.dorm.maven.service.MavenService;
import com.zenika.dorm.maven.test.fixtures.MavenWebServiceRequestFixtures;
import com.zenika.dorm.maven.test.unit.MavenUnitTest;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;

import static org.mockito.Mockito.verify;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenServiceUnitTest extends MavenUnitTest {

    private MavenWebServiceRequestFixtures webServiceRequestFixtures;

    @InjectMocks
    private MavenService mavenService = new MavenService();

    @Mock
    private MavenPomReader pomReader;

    @Mock
    private DormService dormService;

    @Override
    public void before() {
        super.before();
        webServiceRequestFixtures = new MavenWebServiceRequestFixtures();
    }

    @Test
    @Ignore
    public void storeAndReadPom() throws Exception {

        MavenMetadata pomMetadata = metadataFixtures.getCommonsioPomMetadata();

        File pomFile = webServiceRequestFixtures.getPom();
        DormResource pomResource = DefaultDormResource.create(pomFile);

        mavenService.storePom(pomMetadata, pomFile);

//        Dependency junitDependency = dependencyFixtures.getJunitTestDependency();
//        List<Dependency> dependencies = new ArrayList<Dependency>();
//        dependencies.add(junitDependency);
//
//        when(pomReader.getArtifact()).thenReturn(pomMetadata);
//        verify(pomReader).getArtifact();
//
//        when(pomReader.getDependencies()).thenReturn(dependencies);
//        verify(pomReader).getDependencies();

        verify(dormService).storeResource(pomResource, pomMetadata, new DormServiceStoreResourceConfig().override(true));
        verify(dormService).addDependenciesToNode(dependencyFixtures.getCommonsioNode());
    }


}
