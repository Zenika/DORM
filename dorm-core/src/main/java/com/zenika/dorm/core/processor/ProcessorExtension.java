package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormProperties;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;

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

    public void push(DormProperties properties);
}
