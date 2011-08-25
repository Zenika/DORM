package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.put.DormServicePutRequest;
import com.zenika.dorm.core.service.put.DormServicePutResult;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    public DormServicePutResult put(DormServicePutRequest request);

    public DormServiceGetResult get(DormServiceGetRequest request);
}
