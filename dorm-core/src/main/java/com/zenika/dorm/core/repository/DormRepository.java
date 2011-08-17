package com.zenika.dorm.core.repository;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormRepository {

    public boolean put(Dependency dependency);

    public DormResource get(DormMetadata metadata);

    public File getBase();
}
