package com.zenika.dorm.core.graph.visitor.filter;

import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilter;
import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilterChain;
import com.zenika.dorm.core.model.DependencyNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyVisitorCyclicFilter implements DependencyVisitorFilter {

    private Set<DependencyNode> visitedNodes = new HashSet<DependencyNode>();

    @Override
    public void init() {
    }

    @Override
    public void doFilter(DependencyNode node, DependencyVisitorFilterChain filterChain) {

        if (visitedNodes.contains(node)) {
            return;
        }

        visitedNodes.add(node);

        filterChain.forward(node);
    }

    @Override
    public void destroy() {
    }
}
