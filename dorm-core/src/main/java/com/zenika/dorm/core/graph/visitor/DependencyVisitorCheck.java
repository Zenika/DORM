package com.zenika.dorm.core.graph.visitor;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.graph.visitor.impl.DependencyVisitorCheckException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyVisitorCheck {

    public void check(DependencyNode node) throws DependencyVisitorCheckException;
}
