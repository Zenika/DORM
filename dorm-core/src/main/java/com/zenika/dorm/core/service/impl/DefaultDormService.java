package com.zenika.dorm.core.service.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;
import com.zenika.dorm.core.service.DormService;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormService implements DormService {

    @Inject
    private DormDao dao;

    @Override
    public Boolean pushDependency(Dependency dependency) {
        DependencyNodeLeaf node = new DefaultDependencyNodeLeaf(dependency);
        return dao.push(dependency);
    }

    @Override
    public Boolean pushDependency(Dependency dependency, Dependency parent) {
        DependencyNodeComposite nodeParent = new DefaultDependencyNodeComposite(parent);
        DependencyNodeLeaf node = new DefaultDependencyNodeLeaf(dependency);
        nodeParent.addChild(node);
        return dao.push(nodeParent);
    }

    @Override
    public Boolean pushNode(DependencyNode node) {
        return dao.push(node);
    }

    @Override
    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage) {
        return dao.getByMetadata(metadata, usage);
    }
}
