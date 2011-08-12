package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.builder.DependencyNodeBuilderFromRequest;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor for the dorm extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessor extends AbstractProcessorExtension {

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

    @Override
    public DormMetadata getMetadata(DormRequest request) {
        throw new UnsupportedOperationException();
    }
}
