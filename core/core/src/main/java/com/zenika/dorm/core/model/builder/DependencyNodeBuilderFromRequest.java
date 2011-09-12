package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;

/**
 * Builder for dorm dependency node with the respect of immutability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyNodeBuilderFromRequest {

    private Dependency dependency;

    public DependencyNodeBuilderFromRequest(DormWebServiceRequest request, String type,
                                            DormMetadataExtension extension) {
        this.dependency = new DependencyBuilderFromRequest(request, type, extension).build();
    }

    public DependencyNode build() {
        return DefaultDependencyNode.create(dependency);
    }
}
