package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.model.DormMetadata;

import javax.ws.rs.core.MediaType;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoSinglePushTask extends NuxeoAbstractTask {

    private static final String NUXEO_PUT = "save";

    @Inject
    private DormMetadata metadata;

    @Override
    public Void execute() {
        WebResource resource = wrapper.get();

        NuxeoMetadata metadata = new NuxeoMetadata(
                this.metadata.getFunctionalId(),
                this.metadata.getExtensionName(),
                this.metadata.getVersion(),
                serviceLoader.getInstanceOf(this.metadata.getExtensionName()).toMap(this.metadata)
        );

        resource.path(NUXEO_PUT)
                .accept(MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_XML)
                .entity(metadata)
                .put();

        logRequest("PUT", resource, NUXEO_PUT);
        return null;
    }
}
