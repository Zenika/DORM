package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.visitor.DependencyVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of node dependency
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDependencyNode implements DependencyNode {

    private Dependency dependency;

    private Set<DependencyNode> childrens = new HashSet<DependencyNode>();

    public DefaultDependencyNode(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public Dependency getDependency() {
        return dependency;
    }

    @Override
    public void addChildren(DependencyNode node) {
        childrens.add(node);
    }

    @Override
    public Set<DependencyNode> getChildrens() {
        return childrens;
    }

    @Override
    public Boolean accept(DependencyVisitor visitor) {

        if (visitor.visitEnter(this)) {
            for (DependencyNode children : childrens) {
                if (!children.accept(visitor)) {
                    break;
                }

//                children.accept(visitor);
            }
        }

        return visitor.visitExit(this);
    }
}
