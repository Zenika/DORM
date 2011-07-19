package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormProperties;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;

import java.util.Map;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessor extends AbstractProcessorExtension {

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

    /**
     * Create a simple node with the file and the origin
     *
     * @param properties
     * @return the node containing the file and the origin
     */
    @Override
    public DependencyNode push(DormProperties properties) {

        if (!properties.hasFile()) {
            throw new CoreException("File is required");
        }

        if (null == properties.getProperty("qualifier")) {
            throw new CoreException("Qualifier is null");
        }

        DormOrigin origin = new DefaultDormOrigin(properties.getProperty("qualifier"));

        return getHelper().createNode(origin, properties);
    }
}
