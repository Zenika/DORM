package com.zenika.dorm.core.model.graph.proposal1;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract visitor that adds cyclic dependencies management
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class CyclicDependencyVisitor implements DependencyVisitor {

    private Set<DependencyNode> visitedNode = new HashSet<DependencyNode>();

    protected Boolean checkCyclicDependency(DependencyNode node) {
        if (visitedNode.contains(node)) {
            System.out.println("cyclic dependency detected on " +
                    node.getDependency().getArtifact() + ", ignored");
            return false;
        }

        visitedNode.add(node);
        return true;
    }
}
