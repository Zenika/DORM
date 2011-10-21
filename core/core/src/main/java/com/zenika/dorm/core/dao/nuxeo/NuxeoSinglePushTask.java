package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.factory.PluginExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;

import javax.ws.rs.core.MediaType;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoSinglePushTask extends NuxeoAbstractTask {

    private static final String NUXEO_POST = "save";

    @Inject
    private DormMetadata metadata;

    @Override
    public DormMetadata execute() {
        WebResource resource = wrapper.get();

        NuxeoMetadata metadata = new NuxeoMetadata(
                this.metadata.getType(),
                this.metadata.getName(),
                this.metadata.getVersion(),
                serviceLoader.getInstanceOf(this.metadata.getType()).toMap(this.metadata)
        );

        NuxeoMetadata response = resource.path(NUXEO_POST)
                .accept(MediaType.APPLICATION_XML)
                .type(MediaType.APPLICATION_XML)
                .entity(metadata)
                .post(NuxeoMetadata.class);

        logRequest("POST", resource, NUXEO_POST);
        PluginExtensionMetadataFactory factoryPlugin = serviceLoader.getInstanceOf(metadata.getExtensionName());
        return factoryPlugin.fromMap(response.getId(), response.getProperties());
    }
}
