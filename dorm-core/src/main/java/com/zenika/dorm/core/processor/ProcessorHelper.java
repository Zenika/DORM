package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormProperties;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ProcessorHelper {

    public DormFile createFile(DormProperties properties);

    public DormMetadata createMetadata(DormOrigin origin, DormProperties properties);

    public Dependency createDependency(DormMetadata metadata, DormProperties properties);

    public Dependency createDependency(DormMetadata metadata, DormFile file, DormProperties properties);

    /**
     * Create dependency direct from origin
     *
     * @param origin
     * @param properties
     * @return
     */
    public Dependency createDependency(DormOrigin origin, DormProperties properties);

    /**
     * Create dependency direct from origin
     *
     * @param origin
     * @param file
     * @param properties
     * @return
     */
    public Dependency createDependency(DormOrigin origin, DormFile file, DormProperties properties);

    public DependencyNode createNode(Dependency dependency);

    /**
     * Direct proxy for simple node
     *
     * @param origin
     * @param properties
     * @return
     */
    public DependencyNode createNode(DormOrigin origin, DormProperties properties);
}
