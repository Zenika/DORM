package com.zenika.dorm.core.model.graph.proposal1;

/**
 * Graph visitor that prints dependencies in the console
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ConsoleVisitor extends CyclicDependencyVisitor {

    private Integer counter = 0;

    @Override
    public Boolean visitEnter(DependencyNode node) {

        if (!checkCyclicDependency(node)) {
            return false;
        }

        tab(counter);
        System.out.println("Enter node : " + node.getDependency().getArtifact().getFullQualifier() +
                " with usage " + node.getDependency().getUsage());

        if (!node.getChildrens().isEmpty()) {
            counter++;
        }

        return true;
    }

    @Override
    public Boolean visitExit(DependencyNode node) {
        tab(counter);
        System.out.println("Exit node");
        counter--;

        return true;
    }

    private void tab(Integer n) {
        for (int i = 0; i < n * 3; i++) {
            System.out.print("-");
        }
    }
}
