package com.zenika.dorm.core.graph.visitor.impl;

import com.zenika.dorm.core.graph.visitor.DependencyVisitorCheck;
import com.zenika.dorm.core.model.DependencyNode;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DependencyVisitorFileCheck implements DependencyVisitorCheck{

    @Override
    public void check(DependencyNode node) throws DependencyVisitorCheckException {
        if (!node.getDependency().hasFile()){
            throw new DependencyVisitorCheckException("The dependency doesn't have file !");
        }
    }
}
