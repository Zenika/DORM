package com.zenika.dorm.core.graph.visitor;

import com.zenika.dorm.core.model.DependencyNode;

/**
 * Implementation of an hierarchical visitor pattern
 * See http://c2.com/cgi/wiki?HierarchicalVisitorPattern
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyVisitor {

    /**
     * The visitor enters to a composite node
     * Calls automatically performChecks in the DependencyVisitorCheckAspect
     *
     * @param node the composite node
     * @return the visit's enter result
     */
    boolean visitEnter(DependencyNode node);

    boolean visitExit(DependencyNode node);

    boolean collect(DependencyNode node);
}
