package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.service.config.DormServiceGetResourceConfig;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataResult;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataValues;
import com.zenika.dorm.core.service.put.DormServicePutRequest;
import com.zenika.dorm.core.service.put.DormServiceStoreResult;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    public DormServiceStoreResult store(DormServicePutRequest request);

    public DormServiceStoreResult storeMetadata(DormMetadata metadata);

    public void storeResource(DormResource resource, DormServiceStoreResourceConfig config);

    public DormServiceGetMetadataResult getMetadata(DormServiceGetMetadataValues values);

    public DormResource getResource(DormMetadata metadata, DormServiceGetResourceConfig config);

    public DormResource getResource(String extension, String path);
}
