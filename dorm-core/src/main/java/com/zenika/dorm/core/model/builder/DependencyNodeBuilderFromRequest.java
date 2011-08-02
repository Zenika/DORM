package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;

/**
 * Builder for dorm dependency node with the respect of immutability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyNodeBuilderFromRequest {

    private Dependency dependency;

    public DependencyNodeBuilderFromRequest(DormRequest request, DormMetadataExtension extension) {
        this.dependency = new DependencyBuilderFromRequest(request, extension).build();
    }

    public DependencyNode build() {
        return DefaultDependencyNode.create(dependency);
    }
}
