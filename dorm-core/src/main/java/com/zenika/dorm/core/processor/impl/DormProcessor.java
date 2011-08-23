package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.*;
import com.zenika.dorm.core.model.builder.DependencyNodeBuilderFromRequest;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor for the dorm extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessor implements ProcessorExtension {

    private static final Logger LOG = LoggerFactory.getLogger(DormProcessor.class);

    /**
     * Create a simple node with the extension and file if exists
     *
     * @param request
     * @return the node containing the extension and file if exists
     */
    @Override
    public DependencyNode push(DormRequest request) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Dorm request to push = " + request);
        }

        String name = request.getProperty(DefaultDormMetadataExtension.METADATA_NAME);

        if (null == name || name.isEmpty()) {
            throw new CoreException("Name is required.");
        }

        DormMetadataExtension extension = new DefaultDormMetadataExtension(name);

        DependencyNode node = new DependencyNodeBuilderFromRequest(request,
                DefaultDormMetadataExtension.EXTENSION_TYPE, extension).build();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Dorm dependency = " + node.getDependency());
        }

        return node;
    }

    /**
     * @param request
     * @return
     * @deprecated
     */
    public DormMetadata getMetadata(DormRequest request) {

        DefaultDormMetadataExtension extension = new DefaultDormMetadataExtension(
                request.getProperty(DefaultDormMetadataExtension.METADATA_NAME));

        return DefaultDormMetadata.create(request.getVersion(), request.getType(), extension);
    }

    @Override
    public Dependency getDependency(DependencyNode node) {
        return node.getDependency();
    }

    @Override
    public DormServiceGetRequest buildGetRequest(DormRequest request) {
        throw new UnsupportedOperationException();
    }
}
