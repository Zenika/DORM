package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DormMetadata;

import javax.ws.rs.core.MediaType;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoGetTask extends NuxeoAbstractTask {

    private static final String NUXEO_GET = "get/";

    @Inject
    private DormBasicQuery query;

    @Override
    public DormMetadata execute() {
        WebResource resource = wrapper.get();

        String pathParams = buildPathParams(query);

        NuxeoMetadata metadata = resource.path(pathParams)
                .accept(MediaType.APPLICATION_XML)
                .get(NuxeoMetadata.class);

        logRequest("GET", resource, pathParams);

        return serviceLoader.getInstanceOf(metadata.getExtensionName())
                .fromMap(metadata.getId(), metadata.getProperties());
    }

    /**
     * Path order: extensionName/name/version
     *
     * @param query
     * @return
     */
    private String buildPathParams(DormBasicQuery query){
        return new StringBuilder(50)
                .append(NUXEO_GET)
                .append(query.getExtensionName())
                .append("/")
                .append(query.getName())
                .append("/")
                .append(query.getVersion())
                .toString();
    }
}