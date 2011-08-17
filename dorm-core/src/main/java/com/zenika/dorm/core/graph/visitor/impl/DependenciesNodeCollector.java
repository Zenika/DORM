package com.zenika.dorm.core.graph.visitor.impl;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.graph.visitor.AbstractDependencyVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DependenciesNodeCollector extends AbstractDependencyVisitor {

    private Set<DependencyNode> dependencies;
    private Usage usage;

    public DependenciesNodeCollector(Usage usage) {
        this(new HashSet<DependencyNode>(), usage);
    }

    public DependenciesNodeCollector(Set<DependencyNode> dependencies, Usage usage) {
        this.dependencies = dependencies;
        this.usage = usage;
    }

    @Override
    public boolean visitEnter(DependencyNode node) {
        dependencies.add(node);
        return true;
    }

    @Override
    public boolean visitExit(DependencyNode node) {
        return true;
    }

    public Set<DependencyNode> getDependencies() {
        return dependencies;
    }


}
