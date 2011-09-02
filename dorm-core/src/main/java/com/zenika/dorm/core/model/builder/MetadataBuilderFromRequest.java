package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;

/**
 * Builder for metadata from request with the respect of metadata immuability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MetadataBuilderFromRequest {

    private String version;
    private DormMetadataExtension extension;

    public MetadataBuilderFromRequest(String type, DormWebServiceRequest request, DormMetadataExtension extension) {
        this.extension = extension;
    }

    public DormMetadata build() {
        return DefaultDormMetadata.create(version, extension);
    }
}
