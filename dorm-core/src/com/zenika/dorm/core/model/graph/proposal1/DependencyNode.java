package com.zenika.dorm.core.model.graph.proposal1;

import java.util.List;

/**
 * A node represents a dependency in a graph
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyNode {

    Boolean accept(DependencyVisitor visitor);

    void addChildren(DependencyNode node);

    List<DependencyNode> getChildrens();

    void setDependency(Dependency dependency);

    Dependency getDependency();
}
