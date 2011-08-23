package com.zenika.dorm.core.service.impl.get;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.impl.DefaultDormServiceGetRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServiceGetRequestBuilder {

    protected DefaultDormServiceGetRequest request;

    public DefaultDormServiceGetRequestBuilder(String processName, DormMetadataExtension metadataExtension) {
        request = new DefaultDormServiceGetRequest(processName, metadataExtension);
    }

    public DefaultDormServiceGetRequestBuilder repositoryRequest(boolean repositoryRequest) {
        request.setRepositoryRequest(repositoryRequest);
        return this;
    }

    public DormServiceGetRequest build() {
        return request;
    }
}
