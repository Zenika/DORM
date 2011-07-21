package com.zenika.dorm.core.graph.visitor.impl;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.graph.visitor.AbstractDependencyVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependenciesCollector extends AbstractDependencyVisitor {

    private Set<Dependency> dependencies;
    private Usage usage;

    public DependenciesCollector(Usage usage) {
        this(new HashSet<Dependency>(), usage);
    }

    public DependenciesCollector(Set<Dependency> dependencies, Usage usage) {
        this.dependencies = dependencies;
        this.usage = usage;
    }

    @Override
    public Boolean visitEnter(DependencyNode node) {
        dependencies.add(node.getDependency());
        return true;
    }

    @Override
    public Boolean visitExit(DependencyNode node) {
        return true;
    }

    public Set<Dependency> getDependencies() {
        return dependencies;
    }
}