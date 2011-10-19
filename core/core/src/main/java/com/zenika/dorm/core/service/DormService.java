package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.DerivedObject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    public void storeMetadata(DormMetadata metadata);

    public void storeResource(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config);

    public DormResource getResource(DormMetadata metadata);

    public DependencyNode addDependenciesToNode(DependencyNode node);

    boolean isDormMetadataAlreadyExist(DormMetadata dormMetadata);

    DormMetadata updateDormMetadata(DormMetadata dormMetadata);

    DormMetadata createDormMetadata(DormMetadata dormMetadata);

    void storeDerivedObject(DerivedObject derivedObject, File file);
}
