package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ProcessorExtension {

    public DependencyNode push(DormRequest request);

    public DormMetadata getMetadata(DormRequest request);
}
