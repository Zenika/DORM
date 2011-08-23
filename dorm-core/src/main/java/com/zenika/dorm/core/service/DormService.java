package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    public Boolean push(DependencyNode node);

    public DependencyNode get(DormServiceGetRequest request);

    public Dependency getDependency(DormMetadata metadata, Usage usage);

    public DependencyNode getDependencyNode(DormMetadata metadata, Usage usage);
}
