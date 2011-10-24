package com.zenika.dorm.core.repository;

import com.zenika.dorm.core.model.DerivedObject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormRepository {

    void store(DerivedObject derivedObject, File file);
}
