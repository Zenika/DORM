package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessor extends AbstractProcessorExtension {

    /**
     * Create a simple node with the file and the origin
     *
     * @param request
     * @return the node containing the file and the origin
     */
    @Override
    public DependencyNode push(DormRequest request) {

        if (!request.hasFile()) {
            throw new CoreException("File is required");
        }

        if (null == request.getProperty("qualifier")) {
            throw new CoreException("Qualifier is null");
        }

        DormOrigin origin = new DefaultDormOrigin(request.getProperty("qualifier"));

        return getRequestProcessor().createNode(origin, request);
    }
}
