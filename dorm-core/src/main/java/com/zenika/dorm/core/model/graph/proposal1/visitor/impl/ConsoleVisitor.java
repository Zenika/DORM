package com.zenika.dorm.core.model.graph.proposal1.visitor.impl;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
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
    public Boolean visitEnter(DependencyNode node) {
        print("Enter composite node : " + node.getDependency().getMetadata());
        print("Usage : " + node.getDependency().getUsage());
        counter++;

        return true;
    }

    @Override
    public Boolean visitExit(DependencyNode node) {
        print("Exit composite node : " + node.getDependency().getMetadata());
        counter--;

        return true;
    }

    @Override
    public Boolean visit(DependencyNodeLeaf node) {
        print("Visit leaf node : " + node.getDependency().getMetadata());
        return true;
    }

    private void print(String text) {
        for (int i = 0; i < counter * 3; i++) {
            System.out.print("-");
        }
    }
}
