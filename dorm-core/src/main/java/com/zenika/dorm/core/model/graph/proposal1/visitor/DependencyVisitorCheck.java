package com.zenika.dorm.core.model.graph.proposal1.visitor;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.visitor.impl.DependencyVisitorCheckException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyVisitorCheck {

    public void check(DependencyNode node) throws DependencyVisitorCheckException;
}
