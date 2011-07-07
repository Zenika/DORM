package com.zenika.dorm.core.model.graph.proposal1;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of node dependency
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDependencyNode implements DependencyNode {

    private List<DependencyNode> childrens = new ArrayList<DependencyNode>();
    private Dependency dependency;

    public DefaultDependencyNode(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public void addChildren(DependencyNode node) {
        childrens.add(node);
    }

    @Override
    public Boolean accept(DependencyVisitor visitor) {

        if (visitor.visitEnter(this)) {
            for (DependencyNode children : getChildrens()) {
//                if (!children.accept(visitor)) {
//                    continue;
//                }

                children.accept(visitor);
            }
        }

        return visitor.visitExit(this);
    }

    @Override
    public List<DependencyNode> getChildrens() {
        return childrens;
    }

    @Override
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public Dependency getDependency() {
        return dependency;
    }
}
