package com.zenika.dorm.core.model.graph.proposal1;

import com.zenika.dorm.core.model.graph.proposal1.visitor.DependencyVisitor;

/**
 * A node represents a dependency in a graph
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyNode {

    public Boolean accept(DependencyVisitor visitor);

    public void setDependency(Dependency dependency);

    public Dependency getDependency();
}
