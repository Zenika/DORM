package com.zenika.dorm.maven.test.pom;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.maven.model.MavenPluginMetadata;
import com.zenika.dorm.maven.pom.MavenPomReader;
import com.zenika.dorm.maven.test.fixtures.MavenFileFixtures;
import com.zenika.dorm.maven.test.unit.MavenUnitTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenPomReaderUnitTest extends MavenUnitTest {

    private MavenFileFixtures fileFixtures;

    private MavenPomReader pomReader;

    @Override
    public void before() {
        super.before();

        fileFixtures = new MavenFileFixtures();
        pomReader = new MavenPomReader(fileFixtures.getPom());
    }

    @Test
    public void getArtifactFromPom() throws Exception {

        MavenPluginMetadata pomMetadata = metadataFixtures.getCommonsioPomMetadata();

        assertThat(pomReader.getArtifact()).isEqualTo(pomMetadata);
    }

    @Test
    public void getArtifactDependenciesFromPom() throws Exception {
        DormMetadata junitDependency = metadataFixtures.getJunitMetadata().toDormMetadata();
        junitDependency.setUsage(Usage.create("test"));

        List<DormMetadata> dependencies = new ArrayList<DormMetadata>();
        dependencies.add(junitDependency);

        assertThat(pomReader.getDependencies()).isEqualTo(dependencies);
    }
}
