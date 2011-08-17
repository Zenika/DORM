package com.zenika.dorm.core.graph.visitor.impl;

import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilter;
import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilterChain;
import com.zenika.dorm.core.model.DependencyNode;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DependencyVisitorFileFilter implements DependencyVisitorFilter {

    @Override
    public void init() {
    }

    @Override
    public void doFilter(DependencyNode node, DependencyVisitorFilterChain filterChain) {
        if (node.getDependency().hasResource()) {
            filterChain.forward(node);
        }
    }

    @Override
    public void destroy() {
    }
}
