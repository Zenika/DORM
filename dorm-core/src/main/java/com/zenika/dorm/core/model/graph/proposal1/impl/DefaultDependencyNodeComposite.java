package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.visitor.DependencyVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @deprecated
 */
public class DefaultDependencyNodeComposite extends DefaultDependencyNode implements
        DependencyNodeComposite {

    private Set<DependencyNode> childrens = new HashSet<DependencyNode>();

    public DefaultDependencyNodeComposite(Dependency dependency) {
        super(dependency);
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
