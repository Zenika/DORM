package com.zenika.dorm.core.model.graph.proposal1.visitor;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.visitor.impl.DependencyVisitorCheckException;

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
     * The visitor enters to a leaf node
     * Calls automatically performChecks in the DependencyVisitorCheckAspect
     *
     * @param node the leaf node
     * @return the visit's result
     * @deprecated
     */
    Boolean visit(DependencyNodeLeaf node);

    /**
     * Is called automatically by the DependencyVisitorCheckAspect
     *
     * @param node
     * @throws DependencyVisitorCheckException
     *
     */
    public void performChecks(DependencyNode node) throws DependencyVisitorCheckException;
}
