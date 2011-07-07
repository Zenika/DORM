package com.zenika.dorm.core.model.graph.proposal1;

/**
 * Visitor pattern to make some introspection on a dependencies graph
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyVisitor {

    Boolean visitEnter(DependencyNode node);

    Boolean visitExit(DependencyNode node);
}
