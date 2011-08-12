package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;

/**
 * Builder for metadata from request with the respect of metadata immuability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MetadataBuilderFromRequest {

    private String version;
    private String type;
    private DormMetadataExtension extension;

    public MetadataBuilderFromRequest(String type, DormRequest request, DormMetadataExtension extension) {
        this.type = type;
        this.version = request.getVersion();
        this.extension = extension;
    }

    public DormMetadata build() {
        return DefaultDormMetadata.create(version, type, extension);
    }
}
