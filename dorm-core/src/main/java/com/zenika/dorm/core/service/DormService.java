package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    public Boolean pushNode(DependencyNode node);

    public Boolean pushDependency(Dependency dependency);

    public Boolean pushDependency(Dependency dependency, Dependency parent);

    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage);
}
