package com.zenika.dorm.core.model.graph.proposal1.visitor.impl;

import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;
import com.zenika.dorm.core.model.graph.proposal1.visitor.AbstractDependencyVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CollectDependenciesVisitor extends AbstractDependencyVisitor {

    private Set<Dependency> dependencies;
    private Usage usage;

    public CollectDependenciesVisitor(Usage usage) {
        this(new HashSet<Dependency>(), usage);
    }

    public CollectDependenciesVisitor(Set<Dependency> dependencies, Usage usage) {
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

    @Override
    public Boolean visit(DependencyNodeLeaf node) {
        dependencies.add(node.getDependency());
        return true;
    }

    public Set<Dependency> getDependencies() {
        return dependencies;
    }
}
