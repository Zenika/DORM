package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.model.DormMetadata;

import javax.ws.rs.core.MediaType;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoRetrieveByQualifier extends NuxeoAbstractTask {

    private static final String NUXEO_GET = "get/";

    @Inject
    private String qualifier;

    @Override
    public DormMetadata execute() {
        WebResource resource = wrapper.get();

        NuxeoMetadata metadata = resource.path(NUXEO_GET + qualifier)
                .accept(MediaType.APPLICATION_XML)
                .get(NuxeoMetadata.class);

        logRequest("GET", resource, qualifier);

        return serviceLoader.getInstanceOf(metadata.getExtensionName())
                .fromMap(metadata.getId(), metadata.getProperties());
    }
}