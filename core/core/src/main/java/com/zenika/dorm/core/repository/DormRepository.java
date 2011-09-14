package com.zenika.dorm.core.repository;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormRepository {

    public void store(String extension, String path, DormResource resource, boolean override);

    public void store(DormResource resource, DormServiceStoreResourceConfig config);

    public DormResource get(DormMetadata metadata);
}
