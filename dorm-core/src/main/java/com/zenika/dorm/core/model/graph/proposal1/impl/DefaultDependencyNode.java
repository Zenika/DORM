package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;

/**
 * Default implementation of node dependency
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DefaultDependencyNode implements DependencyNode {

    protected Dependency dependency;

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
}
