package com.zenika.dorm.core.model;

import com.zenika.dorm.core.graph.visitor.DependencyVisitor;

import java.util.Set;

/**
 * A node represents a dependency in a graph
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyNode {

    public Boolean accept(DependencyVisitor visitor);

    public Dependency getDependency();

    void addChild(DependencyNode node);

    Set<DependencyNode> getChildren();
}