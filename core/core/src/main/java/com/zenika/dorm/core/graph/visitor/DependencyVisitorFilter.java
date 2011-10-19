package com.zenika.dorm.core.graph.visitor;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyVisitorFilter {

    public void init();

    public void doFilterOnEntrance(DependencyNode node, DependencyVisitorFilterChain filterChain);

    public void doFilterOnExit(DependencyNode node, DependencyVisitorFilterChain filterChain);

    public void destroy();
}
