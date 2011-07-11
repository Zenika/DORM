package com.zenika.dorm.core.model.graph.proposal1.visitor;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;

/**
 * Visitor pattern to make some introspection on a dependencies graph
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyVisitor {

    Boolean visitEnter(DependencyNodeComposite node);

    Boolean visitExit(DependencyNodeComposite node);

    Boolean visit(DependencyNodeLeaf node);
}
