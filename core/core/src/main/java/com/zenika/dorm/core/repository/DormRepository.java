package com.zenika.dorm.core.repository;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormRepository {

    public void store(String extension, String path, DormResource resource, boolean override);

    public DormResource get(DormMetadata metadata);

    public void store(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config);
}