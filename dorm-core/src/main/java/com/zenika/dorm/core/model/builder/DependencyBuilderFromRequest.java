package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormFile;

import java.io.File;

/**
 * Builder for dorm dependency with the respect of immutability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyBuilderFromRequest {

    private DormMetadata metadata;
    private DormFile file;
    private Usage usage;

    private DependencyBuilderFromRequest(DormRequest request) {

        // by default use the usage of the request if exists,
        // but can be overriden by the usage() of the builder
        this.usage = Usage.create(request.getUsage());

        // todo: maybe some improvements can be done here
        if (request.hasFile()) {
            this.file = new DormFileBuilderFromRequest(request).build();
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

    public DependencyBuilderFromRequest file(DormFile file) {
        this.file = file;
        return this;
    }

    public DependencyBuilderFromRequest file(String filename, File file) {
        this.file = DefaultDormFile.create(filename, file);
        return this;
    }

    public DependencyBuilderFromRequest usage(Usage usage) {
        this.usage = usage;
        return this;
    }

    public Dependency build() {
        return DefaultDependency.create(metadata, usage, file);
    }
}
