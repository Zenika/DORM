package com.zenika.dorm.maven.test.fixtures;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.model.MavenPluginMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenDependencyFixtures {

    private MavenMetadataFixtures metadataFixtures;

    private DormMetadata commonsioDependency;
    private DormMetadata junitTestDependency;

    public MavenDependencyFixtures() {
        this(new MavenMetadataFixtures());
    }

    public MavenDependencyFixtures(MavenMetadataFixtures metadataFixtures) {
        this.metadataFixtures = metadataFixtures;

        commonsioDependency = createDormMetadata(metadataFixtures.getCommonsioPomMetadata());
        junitTestDependency = createDormMetadata(metadataFixtures.getJunitMetadata());

        commonsioDependency.addChild(junitTestDependency);
    }

    public DormMetadata getJunitTestDependency() {
        return junitTestDependency;
    }

    public DormMetadata getCommonsioDependency() {
        return commonsioDependency;
    }

    private DormMetadata createDormMetadata(MavenPluginMetadata mavenPluginMetadata){
        DormMetadata dormMetadata = new DormMetadata();
        dormMetadata.setName(mavenPluginMetadata.getArtifactId());
        dormMetadata.setVersion(mavenPluginMetadata.getVersion());
        dormMetadata.addPluginMetadata(mavenPluginMetadata);
        return dormMetadata;
    }
}
