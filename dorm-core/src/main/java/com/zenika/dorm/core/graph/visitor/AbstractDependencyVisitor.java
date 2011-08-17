package com.zenika.dorm.core.graph.visitor;

import com.zenika.dorm.core.graph.visitor.filter.DependencyVisitorCyclicFilter;
import com.zenika.dorm.core.model.DependencyNode;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractDependencyVisitor implements DependencyVisitor {

    private DependencyVisitorFilterChain filterChain = new DependencyVisitorFilterChain();

    protected AbstractDependencyVisitor() {
        filterChain.addFilter(new DependencyVisitorCyclicFilter());
    }

    public final boolean collect(DependencyNode node) {
        filterChain.forward(node);
        return filterChain.isProcessContinue();
    }

    public void addFilter(DependencyVisitorFilter filter) {
        filterChain.addFilter(filter);
    }
}
