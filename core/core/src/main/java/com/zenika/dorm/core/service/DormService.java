package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.DerivedObject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    boolean isDormMetadataAlreadyExist(DormMetadata dormMetadata);

    DormMetadata updateDormMetadata(DormMetadata dormMetadata);

    DormMetadata createDormMetadata(DormMetadata dormMetadata);

    void storeDerivedObject(DerivedObject derivedObject, File file);

    DormMetadata getDormMetadata(DormMetadata dormMetadata, String mavenPlugin);
}
