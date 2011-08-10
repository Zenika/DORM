package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.model.*;
import com.zenika.dorm.core.model.impl.DefaultDormResource;

import java.io.File;

/**
 * Builder for dorm dependency with the respect of immutability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyBuilderFromRequest {

    private DormMetadata metadata;
    private DormResource resource;
    private Usage usage;

    private DependencyBuilderFromRequest(DormRequest request) {

        // by default use the usage of the request if exists,
        // but can be overriden by the usage() of the builder
        this.usage = Usage.create(request.getUsage());

        // todo: maybe some improvements can be done here
        if (request.hasFile()) {
            this.resource = new DormResourceBuilderFromRequest(request).build();
        }
    }

    public DependencyBuilderFromRequest(DormRequest request, DormMetadata metadata) {
        this(request);
        this.metadata = metadata;
    }

    public DependencyBuilderFromRequest(DormRequest request, DormMetadataExtension extension) {
        this(request);
        this.metadata = new MetadataBuilderFromRequest(request, extension).build();
    }

    public DependencyBuilderFromRequest file(DormResource resource) {
        this.resource = resource;
        return this;
    }

    public DependencyBuilderFromRequest file(String filename, File file) {
        this.resource = DefaultDormResource.create(filename, file);
        return this;
    }

    public DependencyBuilderFromRequest usage(Usage usage) {
        this.usage = usage;
        return this;
    }

    public Dependency build() {
        return DefaultDependency.create(metadata, usage, resource);
    }
}
