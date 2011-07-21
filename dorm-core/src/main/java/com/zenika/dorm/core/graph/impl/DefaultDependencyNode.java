package com.zenika.dorm.core.graph.impl;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.visitor.DependencyVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of node dependency
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDependencyNode implements DependencyNode {

    private Dependency dependency;

    private Set<DependencyNode> childrens = new HashSet<DependencyNode>();

    public static DefaultDependencyNode create(Dependency dependency) {
        return new DefaultDependencyNode(dependency);
    }

    /**
     * @param dependency
     * @deprecated Will be private, use factory methods
     */
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
    public void addChild(DependencyNode node) {
        childrens.add(node);
    }

    @Override
    public Set<DependencyNode> getChildren() {
        return childrens;
    }

    @Override
    public Boolean accept(DependencyVisitor visitor) {

        if (visitor.visitEnter(this)) {
            for (DependencyNode children : childrens) {
                if (!children.accept(visitor)) {
                    break;
                }
            }
        }

        return visitor.visitExit(this);
    }
}
