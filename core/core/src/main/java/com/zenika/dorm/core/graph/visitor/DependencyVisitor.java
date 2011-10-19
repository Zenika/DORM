package com.zenika.dorm.core.graph.visitor;

/**
 * Implementation of an hierarchical visitor pattern
 * See http://c2.com/cgi/wiki?HierarchicalVisitorPattern
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyVisitor {

    boolean visitEnter(DependencyNode node);

    boolean visitExit(DependencyNode node);

    boolean collectEnter(DependencyNode node);

    boolean collectExit(DependencyNode node);
}
