package com.zenika.dorm.core.model.graph.proposal1;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DependencyNodeComposite extends DependencyNode {

    void addChildren(DependencyNode node);

    List<DependencyNode> getChildrens();
}
