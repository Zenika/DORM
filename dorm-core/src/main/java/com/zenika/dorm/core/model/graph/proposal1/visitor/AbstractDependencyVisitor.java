package com.zenika.dorm.core.model.graph.proposal1.visitor;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.visitor.impl.DependencyVisitorCheckException;
import com.zenika.dorm.core.model.graph.proposal1.visitor.impl.DependencyVisitorCyclicCheck;

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
