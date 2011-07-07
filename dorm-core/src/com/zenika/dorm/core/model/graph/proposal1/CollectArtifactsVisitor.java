package com.zenika.dorm.core.model.graph.proposal1;

import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CollectArtifactsVisitor extends CyclicDependencyVisitor {

    public CollectArtifactsVisitor(Set<Artifact> artifacts, Usage usage) {
        this.artifacts = artifacts;
        this.usage = usage;
    }

    private Set<Artifact> artifacts;
    private Usage usage;

    private DependencyNode brokenTree;

    @Override
    public Boolean visitEnter(DependencyNode node) {

        if (!checkCyclicDependency(node)) {
            return false;
        }

        if (!usage.equals(node.getDependency().getUsage())) {
            return false;
        }

        artifacts.add(node.getDependency().getArtifact());
        return true;
    }

    @Override
    public Boolean visitExit(DependencyNode node) {
        return true;
    }
}
