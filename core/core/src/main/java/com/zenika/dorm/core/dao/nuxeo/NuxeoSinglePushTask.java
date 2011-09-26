package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;

import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoSinglePushTask extends NuxeoAbstractTask {

    private static final String NUXEO_PUT = "save";

    @Inject
    private DormMetadata metadata;

    @Override
    protected Void execute() {
        WebResource resource = wrapper.get();

        NuxeoMetadata metadata = new NuxeoMetadata(
                this.metadata.getQualifier(),
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
