package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormProperties;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;
import com.zenika.dorm.core.processor.ProcessorExtension;

import java.util.Map;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessor implements ProcessorExtension {

    @Override
    public DependencyNode getOriginAsNode(Map<String, String> properties) {

        DormMetadata metadata = new DefaultDormMetadata(properties.get("version"),
                new DefaultDormOrigin(properties.get("name")));

        DefaultDependency dependency = new DefaultDependency(metadata);
        dependency.setMainDependency(true);

        DependencyNode node = new DefaultDependencyNode(dependency);

        return node;
    }

    @Override
    public DormOrigin getParentOrigin(Map<String, String> properties) {

        return null;
    }

    @Override
    public Map<DormOrigin, Set<DormOrigin>> getOrigins(Map<String, String> properties) {
        return null;
    }

    @Override
    public void push(DormProperties properties) {
        
    }
}
