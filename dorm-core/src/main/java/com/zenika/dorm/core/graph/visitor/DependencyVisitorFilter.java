package com.zenika.dorm.core.graph.visitor;

import com.zenika.dorm.core.model.DependencyNode;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyVisitorFilter {

    public void init();

    public void doFilterOnEntrance(DependencyNode node, DependencyVisitorFilterChain filterChain);

    public void doFilterOnExit(DependencyNode node, DependencyVisitorFilterChain filterChain);

    public void destroy();
}
