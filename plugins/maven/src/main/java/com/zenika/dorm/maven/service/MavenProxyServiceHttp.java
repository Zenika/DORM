package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.provider.ProxyWebResourceWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenProxyServiceHttp implements MavenProxyService {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProxyServiceHttp.class);

    public static final String DATA_ENTRY_POINT_URI = "http://repo1.maven.org/maven2";

    @Inject
    private ProxyWebResourceWrapper wrapper;

    @Override
    public DormResource getArtifact(DormMetadata metadata) {
        WebResource webResource = wrapper.get();
        MavenUri uri = new MavenUri(convertMetadata(metadata));
        LOG.info("Path proxy: {}", uri.getUri());

        File file;

        try {
            file = webResource.path(uri.getUri())
                    .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .get(File.class);
        } catch (UniformInterfaceException e) {
            if (e.getResponse().getStatus() == ClientResponse.Status.NOT_FOUND.getStatusCode()) {
                return null;
            } else {
                throw new CoreException(e);
            }
        }

        return DefaultDormResource.create(uri.getFilename().getFilename(), file);
    }



    private MavenMetadata convertMetadata(DormMetadata dormMetadata) {
        if (!dormMetadata.getClass().equals(MavenMetadata.class)) {
            throw new CoreException("The metadata isn't a Maven metadata");
        }
        return (MavenMetadata) dormMetadata;
    }
}
