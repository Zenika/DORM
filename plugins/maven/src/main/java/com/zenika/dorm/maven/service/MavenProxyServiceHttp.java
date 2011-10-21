package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.service.FileValidator;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.provider.ProxyWebResourceWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenProxyServiceHttp implements MavenProxyService {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProxyServiceHttp.class);

    public static final String DATA_ENTRY_POINT_URI = "http://repo1.maven.org/maven2";

    @Inject
    private ProxyWebResourceWrapper wrapper;

    @Override
    public Object getArtifact(MavenUri mavenUri) {
        WebResource webResource = wrapper.get();
        LOG.info("Path proxy: {}", mavenUri.getUri());
        ClientResponse response;
        try {
            response = webResource.path(mavenUri.getUri())
                    .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .get(ClientResponse.class);
        } catch (UniformInterfaceException e) {
            if (e.getResponse().getStatus() == ClientResponse.Status.NOT_FOUND.getStatusCode()) {
                return null;
            } else {
                throw new CoreException(e);
            }
        }
        if (Integer.parseInt(response.getHeaders().get("Content-length").get(0)) < 10000) {
            return new DefaultDormResource(mavenUri.getFilename().getFilename(),
                    mavenUri.getFilename().getExtension(),
                    response.getEntity(InputStream.class));
        } else {
            File file = response.getEntity(File.class);
            new FileValidator().validateFile(file);
            return DefaultDormResource.create(mavenUri.getFilename().getFilename(), file);
        }
    }

}
