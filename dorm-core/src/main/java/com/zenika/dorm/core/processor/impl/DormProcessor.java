package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.builder.DependencyNodeBuilderFromRequest;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.processor.ProcessorExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor for the dorm extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessor extends ProcessorExtension {

    private static final Logger LOG = LoggerFactory.getLogger(DormProcessor.class);

    /**
     * Create a simple node with the extension and file if exists
     *
     * @param request
     * @return the node containing the extension and file if exists
     */

    public DormWebServiceResult push(DormWebServiceRequest request) {

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

        return null;
    }

    /**
     * @param request
     * @return
     * @deprecated
     */
    public DormMetadata getMetadata(DormWebServiceRequest request) {

        DefaultDormMetadataExtension extension = new DefaultDormMetadataExtension(
                request.getProperty(DefaultDormMetadataExtension.METADATA_NAME));

        //todo: fix null version
        return DefaultDormMetadata.create(null, extension);
    }

    @Override
    public DormWebServiceResult get(DormWebServiceRequest request) {
        return null;
    }
}
