package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.visitor.DependencyVisitor;

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
