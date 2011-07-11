package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.model.graph.proposal1.*;
import com.zenika.dorm.core.model.graph.proposal1.visitor.DependencyVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDependencyNodeComposite extends DefaultDependencyNode implements
        DependencyNodeComposite {

    private List<DependencyNode> childrens = new ArrayList<DependencyNode>();

    public DefaultDependencyNodeComposite(Dependency dependency) {
        super(dependency);
    }

    @Override
    public void addChildren(DependencyNode node) {
        childrens.add(node);
    }

    @Override
    public List<DependencyNode> getChildrens() {
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
