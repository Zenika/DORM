package com.zenika.dorm.core.service.impl.get;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServiceGetRequestBuilder {

    protected DefaultDormServiceGetRequest request;

    public DefaultDormServiceGetRequestBuilder(String processName, DormMetadataExtension metadataExtension) {
        request = new DefaultDormServiceGetRequest(processName, metadataExtension);
    }

    public DefaultDormServiceGetRequestBuilder version(String version) {
        request.getValues().setVersion(version);
        return this;
    }

    public DefaultDormServiceGetRequestBuilder qualifier(String qualifier) {
        request.getValues().setQualifier(qualifier);
        return this;
    }

    public DefaultDormServiceGetRequestBuilder usage(String usage) {
        return usage(Usage.create(usage));
    }

    public DefaultDormServiceGetRequestBuilder usage(Usage usage) {
        request.getValues().setUsage(usage);
        return this;
    }

    public DefaultDormServiceGetRequestBuilder repositoryRequest(boolean repositoryRequest) {
        request.setRepositoryRequest(repositoryRequest);
        return this;
    }

    public DormServiceGetRequest build() {
        return request;
    }
}
