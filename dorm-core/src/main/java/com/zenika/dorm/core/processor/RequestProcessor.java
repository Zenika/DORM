package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface RequestProcessor {

    public DormFile createFile(DormRequest request);

    public DormMetadata createMetadata(DormMetadataExtension extension, DormRequest request);

    public Dependency createDependency(DormMetadata metadata, DormRequest request);

    public Dependency createDependency(DormMetadata metadata, DormFile file, DormRequest request);

    /**
     * Create dependency direct from origin
     *
     * @param extension
     * @param request
     * @return
     */
    public Dependency createDependency(DormMetadataExtension extension, DormRequest request);

    /**
     * Create dependency direct from origin
     *
     * @param extension
     * @param file
     * @param request
     * @return
     */
    public Dependency createDependency(DormMetadataExtension extension, DormFile file, DormRequest request);

    public DependencyNode createNode(Dependency dependency);

    /**
     * Create simple node from origin
     *
     * @param extension
     * @param request
     * @return
     */
    public DependencyNode createNode(DormMetadataExtension extension, DormRequest request);
}
