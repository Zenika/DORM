package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.graph.visitor.DependencyVisitor;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;

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

    private DefaultDependencyNode(Dependency dependency) {
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

        if (visitor.collectEnter(this)) {
            for (DependencyNode children : childrens) {
                if (!children.accept(visitor)) {
                    break;
                }
            }
        }

        return visitor.collectExit(this);
    }

    @Override
    public String toString() {
        return "DefaultDependencyNode { " +
                "Dependency = " + dependency + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultDependencyNode)) return false;

        DefaultDependencyNode that = (DefaultDependencyNode) o;

        if (dependency != null ? !dependency.equals(that.dependency) : that.dependency != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return dependency != null ? dependency.hashCode() : 0;
    }
}
