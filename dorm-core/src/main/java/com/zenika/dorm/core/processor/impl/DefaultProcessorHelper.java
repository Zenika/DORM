package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormProperties;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;
import com.zenika.dorm.core.model.impl.DefaultDormFile;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.processor.ProcessorHelper;

/**
 * Default implementation
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @see ProcessorHelper
 */
public class DefaultProcessorHelper implements ProcessorHelper {

    @Override
    public DormFile createFile(DormProperties properties) {

        if (!properties.hasFile()) {
            return null;
        }

        return new DefaultDormFile(properties.getFilename(), properties.getFile());
    }

    @Override
    public DormMetadata createMetadata(DormOrigin origin, DormProperties properties) {
        return new DefaultDormMetadata(properties.getVersion(), origin);
    }

    @Override
    public Dependency createDependency(DormMetadata metadata, DormProperties properties) {
        return createDependency(metadata, null, properties);
    }

    @Override
    public Dependency createDependency(DormMetadata metadata, DormFile file, DormProperties properties) {
        return new DefaultDependency(metadata, Usage.create(properties.getUsage()), file);
    }

    @Override
    public Dependency createDependency(DormOrigin origin, DormProperties properties) {
        return createDependency(createMetadata(origin, properties), properties);
    }

    @Override
    public Dependency createDependency(DormOrigin origin, DormFile file, DormProperties properties) {
        return createDependency(createMetadata(origin, properties), file, properties);
    }

    @Override
    public DependencyNode createNode(Dependency dependency) {
        return new DefaultDependencyNode(dependency);
    }

    @Override
    public DependencyNode createNode(DormOrigin origin, DormProperties properties) {

        Dependency dependency = createDependency(createMetadata(origin, properties), createFile(properties),
                properties);

        return createNode(dependency);
    }
}