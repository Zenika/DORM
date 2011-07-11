package com.zenika.dorm.core.model.graph.proposal1.visitor.impl;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.impl.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;
import com.zenika.dorm.core.model.graph.proposal1.visitor.AbstractDependencyVisitor;

import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CollectArtifactsVisitor extends AbstractDependencyVisitor {

    public CollectArtifactsVisitor(Set<DormMetadata> dormMetadatas, Usage usage) {
        this.dormMetadatas = dormMetadatas;
        this.usage = usage;
    }

    private Set<DormMetadata> dormMetadatas;
    private Usage usage;

    private DependencyNode brokenTree;

    @Override
    public Boolean visitEnter(DependencyNodeComposite node) {


        if (!usage.equals(node.getDependency().getUsage())) {
            return false;
        }

        dormMetadatas.add(node.getDependency().getDormMetadata());
        return true;
    }

    @Override
    public Boolean visitExit(DependencyNodeComposite node) {
        return true;
    }

    @Override
    public Boolean visit(DependencyNodeLeaf node) {
        return null;
    }
}
