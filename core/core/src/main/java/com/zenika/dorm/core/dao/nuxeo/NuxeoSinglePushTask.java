package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;

import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoSinglePushTask extends NuxeoAbstractTask {

    private static final String NUXEO_PUT = "http://localhost:???/????????";

    @Inject
    private DormMetadata metadata;

    @Override
    protected Void execute() {
        try {
            WebResource resource = wrapper.get();

            URI nuxeoPutUri = new URI(NUXEO_PUT);


            NuxeoMetadata metadata = new NuxeoMetadata(
                    this.metadata.getQualifier(),
                    this.metadata.getExtensionName(),
                    serviceLoader.getInstanceOf(this.metadata.getExtensionName()).toMap(this.metadata)
            );

            resource.uri(nuxeoPutUri)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(metadata)
                    .put();

            logRequest("PUT", nuxeoPutUri);
            return null;
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
    }
}
