package com.zenika.dorm.maven.test.fixtures;

import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.Usage;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenDependencyFixtures {

    private MavenMetadataFixtures metadataFixtures;

    private Dependency commonsioDependency;
    private Dependency junitTestDependency;

    private DependencyNode commonsioNode;
    private DependencyNode junitTestNode;

    public MavenDependencyFixtures() {
        this(new MavenMetadataFixtures());
    }

    public MavenDependencyFixtures(MavenMetadataFixtures metadataFixtures) {
        this.metadataFixtures = metadataFixtures;

        commonsioDependency = DefaultDependency.create(metadataFixtures.getCommonsioPomMetadata());
        junitTestDependency = DefaultDependency.create(metadataFixtures.getJunitMetadata(), Usage.create("test"));

        commonsioNode = DefaultDependencyNode.create(commonsioDependency);
        junitTestNode = DefaultDependencyNode.create(junitTestDependency);
        commonsioNode.addChild(junitTestNode);
    }

    public Dependency getJunitTestDependency() {
        return junitTestDependency;
    }

    public Dependency getCommonsioDependency() {
        return commonsioDependency;
    }

    public DependencyNode getCommonsioNode() {
        return commonsioNode;
    }

    public DependencyNode getJunitTestNode() {
        return junitTestNode;
    }
}
