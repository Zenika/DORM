package com.zenika.dorm.core.model.graph.proposal1.visitor.impl;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.visitor.AbstractDependencyVisitor;

/**
 * Graph visitor that prints dependencies in the console
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ConsoleVisitor extends AbstractDependencyVisitor {

    private Integer counter = 0;

    @Override
    public Boolean visitEnter(DependencyNodeComposite node) {


        tab(counter);
        System.out.println("Enter node : " + node.getDependency().getDormMetadata().getFullQualifier() +
                " with usage " + node.getDependency().getUsage());

        if (!node.getChildrens().isEmpty()) {
            counter++;
        }

        return true;
    }

    @Override
    public Boolean visitExit(DependencyNodeComposite node) {
        tab(counter);
        System.out.println("Exit node");
        counter--;

        return true;
    }

    @Override
    public Boolean visit(DependencyNodeLeaf node) {
        return null;
    }

    private void tab(Integer n) {
        for (int i = 0; i < n * 3; i++) {
            System.out.print("-");
        }
    }
}
