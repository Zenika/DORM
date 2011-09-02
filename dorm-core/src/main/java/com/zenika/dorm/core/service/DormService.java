package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.service.config.DormServiceGetResourceConfig;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataValues;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.put.DormServicePutRequest;
import com.zenika.dorm.core.service.put.DormServiceStoreResult;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    public DormServiceStoreResult store(DormServicePutRequest request);

    public DormServiceStoreResult storeMetadata(DormMetadata metadata);

    public DormServiceStoreResult storeResource(DormResource resource, DormServiceStoreResourceConfig config);

    public DormServiceGetResult getMetadata(DormServiceGetMetadataValues values);

    public DormServiceGetResult getResource(DormMetadata metadata, DormServiceGetResourceConfig config);
}
