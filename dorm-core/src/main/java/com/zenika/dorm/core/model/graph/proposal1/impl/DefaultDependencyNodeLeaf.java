package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.graph.visitor.DependencyVisitor;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @deprecated
 */
public class DefaultDependencyNodeLeaf extends DefaultDependencyNode implements DependencyNodeLeaf {

    public DefaultDependencyNodeLeaf(Dependency dependency) {
        super(dependency);
    }

    @Override
    public Boolean accept(DependencyVisitor visitor) {
        return visitor.visit(this);
    }
}
