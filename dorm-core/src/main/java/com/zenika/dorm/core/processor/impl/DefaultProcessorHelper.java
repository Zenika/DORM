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
        return new DefaultDependency(metadata, new Usage(properties.getUsage()), file);
    }

    @Override
    public DependencyNode createDependencyNode(Dependency dependency) {
        return new DefaultDependencyNode(dependency);
    }
}