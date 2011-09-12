package com.zenika.dorm.core.repository;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.service.put.DormServicePutValues;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormRepository {

    public boolean put(Dependency dependency);

    public boolean store(DormResource resource, DormServicePutValues values);

    public void store(String extension, String path, DormResource resource, boolean override);

    public DormResource get(DormMetadata metadata);

    public DormResource get(String extension, String path);

    public File getBase();
}
