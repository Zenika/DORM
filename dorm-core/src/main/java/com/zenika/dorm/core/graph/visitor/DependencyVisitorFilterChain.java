package com.zenika.dorm.core.graph.visitor;

import com.zenika.dorm.core.model.DependencyNode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyVisitorFilterChain {

    private Set<DependencyVisitorFilter> filters = new HashSet<DependencyVisitorFilter>();
    private Iterator<DependencyVisitorFilter> iterator;
    private DependencyVisitor visitor;

    private boolean processContinue;

    public void addFilter(DependencyVisitorFilter filter) {
        filters.add(filter);
    }

    public void forward(DependencyNode node) {

        if (null == iterator) {
            iterator = filters.iterator();
        }

        if (iterator.hasNext()) {
            iterator.next().doFilter(node, this);
        } else {
            processContinue = visitor.visitEnter(node);
        }
    }

    public boolean isProcessContinue() {
        return processContinue;
    }
}
