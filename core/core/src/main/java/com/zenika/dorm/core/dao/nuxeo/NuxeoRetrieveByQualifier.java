package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoRetrieveByQualifier extends NuxeoAbstractTask{

    private static final String NUXEO_GET_URI = "http://localhost:????/?????????";

    @Inject
    private String qualifier;

    @Override
    protected DormMetadata execute() {
        try {
            WebResource resource = wrapper.get();

            URI nuxeoGetUri = new URI(new StringBuilder(50)
                    .append(NUXEO_GET_URI)
                    .append("/")
                    .append(qualifier)
                    .toString()
            );

            NuxeoMetadata metadata = resource.uri(nuxeoGetUri)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get(NuxeoMetadata.class);

            logRequest("GET", nuxeoGetUri);

            return serviceLoader.getInstanceOf(metadata.getExtensionName())
                    .createFromProperties(metadata.getProperties());
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
    }
}
