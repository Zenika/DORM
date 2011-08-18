package com.zenika.dorm.core.test.helper;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormGraphFixtures extends DormFixtures {

    private String name2 = "testname2";
    private String name3 = "testname3";
    private String name4 = "testname4";
    private String name5 = "testname5";

    public DormMetadataExtension getMetadataExtension2() {
        return new DefaultDormMetadataExtension(name2);
    }

    public DormMetadataExtension getMetadataExtension3() {
        return new DefaultDormMetadataExtension(name3);
    }

    public DormMetadataExtension getMetadataExtension4() {
        return new DefaultDormMetadataExtension(name4);
    }

    public DormMetadataExtension getMetadataExtension5() {
        return new DefaultDormMetadataExtension(name4);
    }

    public DormMetadata getMetadata2() {
        return DefaultDormMetadata.create(getRequestVersion(), getType(), getMetadataExtension2());
    }

    public DormMetadata getMetadata3() {
        return DefaultDormMetadata.create(getRequestVersion(), getType(), getMetadataExtension3());
    }

    public DormMetadata getMetadata4() {
        return DefaultDormMetadata.create(getRequestVersion(), getType(), getMetadataExtension4());
    }

    public DormMetadata getMetadata5() {
        return DefaultDormMetadata.create(getRequestVersion(), getType(), getMetadataExtension4());
    }

    public Dependency getDependencyWithFile2() {
        return DefaultDependency.create(getMetadata2(), getDormResource());
    }

    public Dependency getDependencyWithFile3() {
        return DefaultDependency.create(getMetadata3(), getDormResource());
    }

    public Dependency getDependencyWithFile4() {
        return DefaultDependency.create(getMetadata4(), getDormResource());
    }

    public Dependency getDependencyWithoutFile5() {
        return DefaultDependency.create(getMetadata5());
    }

    public DependencyNode getNodeWithFile2() {
        return DefaultDependencyNode.create(getDependencyWithFile2());
    }

    public DependencyNode getNodeWithFile3() {
        return DefaultDependencyNode.create(getDependencyWithFile3());
    }

    public DependencyNode getNodeWithFile4() {
        return DefaultDependencyNode.create(getDependencyWithFile4());
    }

    public DependencyNode getNodeWithoutFile5() {
        return DefaultDependencyNode.create(getDependencyWithoutFile5());
    }

    public DependencyNode getSimpleGraph() {

        DependencyNode node3 = getNodeWithFile3();
        DependencyNode node2 = getNodeWithFile2();
        DependencyNode node = getNodeWithFile();

        node2.addChild(node3);
        node.addChild(node2);

        return node;
    }

    public DependencyNode getCyclicGraph() {

        DependencyNode node3 = getNodeWithFile3();
        DependencyNode node2 = getNodeWithFile2();
        DependencyNode node = getNodeWithFile();

        node3.addChild(node);
        node2.addChild(node3);
        node.addChild(node2);

        return node;
    }

    public DependencyNode getCyclicGraphWithNoResource() {

        DependencyNode node = getSimpleGraph();
        DependencyNode node4 = getNodeWithFile();
        DependencyNode node5 = getNodeWithoutFile5();

        node4.addChild(node5);
        node.addChild(node4);

        return node;
    }

    public DependencyNode getCyclicGraphWithNoResourceAndNotLinear() {

        DependencyNode node = getCyclicGraph();
        DependencyNode node4 = getNodeWithFile();
        DependencyNode node5 = getNodeWithoutFile5();

        node5.addChild(node4);
        node.addChild(node5);

        return node;
    }
}
