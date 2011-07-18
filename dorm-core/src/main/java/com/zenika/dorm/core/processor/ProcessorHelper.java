package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormProperties;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ProcessorHelper {

    public DormFile createFile(DormProperties properties);

    public DormMetadata createMetadata(DormOrigin origin, DormProperties properties);

    public Dependency createDependency(DormMetadata metadata, DormProperties properties);

    public Dependency createDependency(DormMetadata metadata, DormFile file, DormProperties properties);

    public DependencyNode createDependencyNode(Dependency dependency);
}
