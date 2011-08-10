package com.zenika.dorm.core.graph.visitor;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.graph.visitor.impl.DependencyVisitorCheckException;

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
    Boolean visitEnter(DependencyNode node);

    Boolean visitExit(DependencyNode node);

    /**
     * Is called automatically by the DependencyVisitorCheckAspect
     *
     * @param node
     * @throws DependencyVisitorCheckException
     *
     */
    public void performChecks(DependencyNode node) throws DependencyVisitorCheckException;
}
