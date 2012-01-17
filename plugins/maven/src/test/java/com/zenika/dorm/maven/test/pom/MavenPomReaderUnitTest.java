package com.zenika.dorm.maven.test.pom;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.pom.MavenPomReader;
import com.zenika.dorm.maven.test.fixtures.MavenWebServiceRequestFixtures;
import com.zenika.dorm.maven.test.unit.MavenUnitTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenPomReaderUnitTest extends MavenUnitTest {

    private MavenWebServiceRequestFixtures webServiceRequestFixtures;

    private MavenPomReader pomReader;

    @Override
    public void before() {
        super.before();

        webServiceRequestFixtures = new MavenWebServiceRequestFixtures();
        pomReader = new MavenPomReader(webServiceRequestFixtures.getPom());
    }

    @Test
    public void getArtifactFromPom() throws Exception {

        MavenMetadata pomMetadata = metadataFixtures.getCommonsioPomMetadata();

        assertThat(pomReader.getArtifact()).isEqualTo(pomMetadata);
    }

    @Test
    public void getArtifactDependenciesFromPom() throws Exception {

        Dependency junitDependency = DefaultDependency.create(metadataFixtures.getJunitMetadata(), Usage.create("test"));
        List<Dependency> dependencies = new ArrayList<Dependency>();
        dependencies.add(junitDependency);
        // TODO: Replace when MavenService is updated
//        assertThat(pomReader.getDependencies()).isEqualTo(dependencies);
    }
}
