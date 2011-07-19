package com.zenika.dorm.core.graph.visitor;

import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.visitor.impl.DependencyVisitorCheckException;
import com.zenika.dorm.core.graph.visitor.impl.DependencyVisitorCyclicCheck;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractDependencyVisitor implements DependencyVisitor {

    private Set<DependencyVisitorCheck> checks = new HashSet<DependencyVisitorCheck>();

    protected AbstractDependencyVisitor() {
        addCheck(new DependencyVisitorCyclicCheck());
    }

    protected void addCheck(DependencyVisitorCheck check) {
        checks.add(check);
    }

    @Override
    public void performChecks(DependencyNode node) throws DependencyVisitorCheckException {
        for (DependencyVisitorCheck check : checks) {
            check.check(node);
        }
    }
}
