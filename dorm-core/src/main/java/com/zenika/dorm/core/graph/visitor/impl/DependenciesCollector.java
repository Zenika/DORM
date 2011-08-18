package com.zenika.dorm.core.graph.visitor.impl;

import com.zenika.dorm.core.graph.visitor.AbstractDependencyVisitor;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.impl.Usage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependenciesCollector extends AbstractDependencyVisitor {

    private static final Logger LOG = LoggerFactory.getLogger(DependenciesCollector.class);

    private Set<Dependency> dependencies;
    private Usage usage;

    public DependenciesCollector() {
        this(Usage.create());
    }

    public DependenciesCollector(Usage usage) {
        this(new LinkedHashSet<Dependency>(), usage);
    }

    public DependenciesCollector(Set<Dependency> dependencies, Usage usage) {
        this.dependencies = dependencies;
        this.usage = usage;
    }

    @Override
    public boolean visitEnter(DependencyNode node) {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Dependency collector visit enter for node : " + node);
        }

        dependencies.add(node.getDependency());
        return true;
    }

    @Override
    public boolean visitExit(DependencyNode node) {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Dependency collector visit exit for node : " + node);
        }

        return true;
    }

    public Set<Dependency> getDependencies() {
        return dependencies;
    }
}
