package com.zenika.dorm.core.service;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    public Boolean push(DependencyNode node);

    public DependencyNode get(DependencyNode node);

    public Dependency getDependency(DormMetadata metadata, Usage usage);
}
