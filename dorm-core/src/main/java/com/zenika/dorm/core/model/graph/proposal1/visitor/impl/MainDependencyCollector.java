package com.zenika.dorm.core.model.graph.proposal1.visitor.impl;

import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.visitor.AbstractDependencyVisitor;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MainDependencyCollector extends AbstractDependencyVisitor {

    private Dependency dependency;

    @Override
    public Boolean visitEnter(DependencyNode node) {

        if (node.getDependency().isMainDependency()) {
            dependency = node.getDependency();
            return false;
        }

        return true;
    }

    @Override
    public Boolean visitExit(DependencyNode node) {
        return true;
    }

    @Override
    public Boolean visit(DependencyNodeLeaf node) {
        return false;
    }

    public Dependency getDependency() {
        return dependency;
    }
}
