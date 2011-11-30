package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    public void storeMetadata(DormMetadata metadata);

    public void storeResource(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config);

    public DormResource getResource(DormMetadata metadata, String extension);

    public DependencyNode addDependenciesToNode(DependencyNode node);
}
