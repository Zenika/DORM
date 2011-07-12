package com.zenika.dorm.core.model.graph.proposal1.visitor.impl;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.visitor.DependencyVisitorCheck;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyVisitorCyclicCheck implements DependencyVisitorCheck {

    private Set<DependencyNodeComposite> visitedNodes = new HashSet<DependencyNodeComposite>();

    @Override
    public void check(DependencyNode node) throws DependencyVisitorCheckException {

        if (!(node instanceof DependencyNodeComposite)) {
            return;
        }

        DependencyNodeComposite compositeNode = (DependencyNodeComposite) node;

        if (visitedNodes.contains(compositeNode)) {
            throw new DependencyVisitorCheckException("cyclic dependency detected on " +
                    node.getDependency().getMetadata());
        }

        visitedNodes.add(compositeNode);
    }
}
