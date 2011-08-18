package com.zenika.dorm.core.graph.visitor;

import com.zenika.dorm.core.graph.visitor.filter.CyclicDependencyVisitorFilter;
import com.zenika.dorm.core.model.DependencyNode;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractDependencyVisitor implements DependencyVisitor {

    private DependencyVisitorFilterChain filterChain = new DependencyVisitorFilterChain(this);

    protected AbstractDependencyVisitor() {
        filterChain.addFilter(new CyclicDependencyVisitorFilter());
    }

    @Override
    public final boolean collectEnter(DependencyNode node) {
        filterChain.process(node);
        return filterChain.isProcessContinue();
    }

    @Override
    public final boolean collectExit(DependencyNode node) {
        filterChain.changeOrientationToBackward();
        filterChain.process(node);
        return filterChain.isProcessContinue();
    }

    public void addFilter(DependencyVisitorFilter filter) {
        filterChain.addFilter(filter);
    }
}
