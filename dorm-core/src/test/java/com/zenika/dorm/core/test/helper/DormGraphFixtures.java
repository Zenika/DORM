package com.zenika.dorm.core.test.helper;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormGraphFixtures extends DormFixtures {

    public Dependency getDependencyWithResource2() {
        return DefaultDependency.create(DefaultDormMetadata.create(getRequestVersion(), getType(),
                new DefaultDormMetadataExtension("dependency with file 2")), getDormResource());
    }

    public Dependency getDependencyWithResource3() {
        return DefaultDependency.create(DefaultDormMetadata.create(getRequestVersion(), getType(),
                new DefaultDormMetadataExtension("dependency with file 3")), getDormResource());
    }

    public Dependency getDependencyWithResource4() {
        return DefaultDependency.create(DefaultDormMetadata.create(getRequestVersion(), getType(),
                new DefaultDormMetadataExtension("dependency with file 4")), getDormResource());
    }

    public Dependency getDependencyWithResource5() {
        return DefaultDependency.create(DefaultDormMetadata.create(getRequestVersion(), getType(),
                new DefaultDormMetadataExtension("dependency with file 5")), getDormResource());
    }

    public Dependency getDependencyWithoutResource2() {
        return DefaultDependency.create(DefaultDormMetadata.create(getRequestVersion(), getType(),
                new DefaultDormMetadataExtension("dependency without file 1")));
    }

    public DependencyNode getNodeWithResource2() {
        return DefaultDependencyNode.create(getDependencyWithResource2());
    }

    public DependencyNode getNodeWithResource3() {
        return DefaultDependencyNode.create(getDependencyWithResource3());
    }

    public DependencyNode getNodeWithResource4() {
        return DefaultDependencyNode.create(getDependencyWithResource4());
    }

    public DependencyNode getNodeWithResource5() {
        return DefaultDependencyNode.create(getDependencyWithResource5());
    }

    public DependencyNode getNodeWithoutResource2() {
        return DefaultDependencyNode.create(getDependencyWithoutResource2());
    }

    /**
     * Graph representation :
     * 1
     * -- 2
     * ---- 4
     * -- 3
     *
     * @return the graph
     */
    public DependencyNode getSimpleGraph() {

        DependencyNode node4 = getNodeWithResource4();
        DependencyNode node3 = getNodeWithResource3();
        DependencyNode node2 = getNodeWithResource2();
        DependencyNode node = getNodeWithResource();

        node2.addChild(node4);
        node.addChild(node2);
        node.addChild(node3);

        return node;
    }

    /**
     * Graph representation :
     * 1
     * -- 2
     * ---- 3
     * ------ 1 -> cyclic dep
     * -- 4
     *
     * @return the graph
     */
    public DependencyNode getCyclicGraph() {

        DependencyNode node4 = getNodeWithResource4();
        DependencyNode node3 = getNodeWithResource3();
        DependencyNode node2 = getNodeWithResource2();
        DependencyNode node = getNodeWithResource();

        node3.addChild(node);
        node2.addChild(node3);
        node.addChild(node2);
        node.addChild(node4);

        return node;
    }

    /**
     * Graph representation :
     * 1
     * -- 2
     * ---- 3
     * ------ 1 -> cyclic dep
     * -- 4
     * -- 5
     *
     * @return the graph
     */
    public DependencyNode getCyclicGraphWithNoResource() {

        DependencyNode node = getCyclicGraph();
        DependencyNode node5 = getNodeWithoutResource();

        node.addChild(node5);

        return node;
    }

    /**
     * Graph representation :
     * 1
     * -- 2
     * ---- 3
     * ------ 1 -> cyclic dep
     * -- 4
     * -- 5 -> no resource
     * ---- 6
     *
     * @return the graph
     */
    public DependencyNode getCyclicGraphWithNoResourceAndNotLinear() {

        DependencyNode node = getCyclicGraph();
        DependencyNode node5 = getNodeWithoutResource();
        DependencyNode node6 = getNodeWithResource5();

        node5.addChild(node6);
        node.addChild(node5);

        return node;
    }
}
