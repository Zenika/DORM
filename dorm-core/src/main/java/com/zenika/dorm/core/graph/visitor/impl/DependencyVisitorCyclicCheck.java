package com.zenika.dorm.core.graph.visitor.impl;

import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.visitor.DependencyVisitorCheck;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyVisitorCyclicCheck implements DependencyVisitorCheck {

    private Set<DependencyNode> visitedNodes = new HashSet<DependencyNode>();

    @Override
    public void check(DependencyNode node) throws DependencyVisitorCheckException {

        if (visitedNodes.contains(node)) {
            throw new DependencyVisitorCheckException("cyclic dependency detected on " +
                    node.getDependency().getMetadata());
        }

        visitedNodes.add(node);
    }
}
