package com.zenika.dorm.core.model.helper;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;

/**
 * Factory for simple models
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormFactoryHelper {

    public static Dependency createSimpleDependency(String version, DormMetadataExtension extension) {
        return DefaultDependency.create(DefaultDormMetadata.create(version, extension));
    }

    public static DependencyNode createSimpleNode(String version, DormMetadataExtension extension) {
        return DefaultDependencyNode.create(createSimpleDependency(version, extension));
    }
}