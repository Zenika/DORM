package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormRequest;

import java.util.Map;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ProcessorExtension {

    /**
     * @param properties
     * @return
     * @deprecated
     */
    public Map<DormOrigin, Set<DormOrigin>> getOrigins(Map<String, String> properties);

    public DependencyNode getOriginAsNode(Map<String, String> properties);

    public DormOrigin getParentOrigin(Map<String, String> properties);

    public DependencyNode push(DormRequest request);
}
