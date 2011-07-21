package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.impl.DefaultDormFile;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.processor.RequestProcessor;

/**
 * Default implementation
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @see com.zenika.dorm.core.processor.RequestProcessor
 */
public class DefaultRequestProcessor implements RequestProcessor {

    @Override
    public DormFile createFile(DormRequest request) {

        if (!request.hasFile()) {
            return null;
        }

        return new DefaultDormFile(request.getFilename(), request.getFile());
    }

    @Override
    public DormMetadata createMetadata(DormMetadataExtension extension, DormRequest request) {
        return new DefaultDormMetadata(request.getVersion(), extension);
    }

    @Override
    public Dependency createDependency(DormMetadata metadata, DormRequest request) {
        return createDependency(metadata, null, request);
    }

    @Override
    public Dependency createDependency(DormMetadata metadata, DormFile file, DormRequest request) {
        return new DefaultDependency(metadata, Usage.create(request.getUsage()), file);
    }

    @Override
    public Dependency createDependency(DormMetadataExtension extension, DormRequest request) {
        return createDependency(createMetadata(extension, request), request);
    }

    @Override
    public Dependency createDependency(DormMetadataExtension extension, DormFile file, DormRequest request) {
        return createDependency(createMetadata(extension, request), file, request);
    }

    @Override
    public DependencyNode createNode(Dependency dependency) {
        return new DefaultDependencyNode(dependency);
    }

    @Override
    public DependencyNode createNode(DormMetadataExtension extension, DormRequest request) {

        Dependency dependency = createDependency(createMetadata(extension, request), createFile(request),
                request);

        return createNode(dependency);
    }
}