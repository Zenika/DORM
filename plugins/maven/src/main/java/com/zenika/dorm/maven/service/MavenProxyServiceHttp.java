package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.MavenRemoteRepository;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.provider.ProxyWebResourceWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenProxyServiceHttp implements MavenProxyService {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProxyServiceHttp.class);

    @Inject
    private ProxyWebResourceWrapper wrapper;

    @Override
    public DormResource getArtifact(DormMetadata metadata, MavenRemoteRepository remoteRepository) {
        WebResource webResource = wrapper.get();
        MavenUri mavenUri = new MavenUri(convertMetadata(metadata));
        String uri = remoteRepository.getUrl() + "/" + mavenUri.getUri();
        LOG.info("Path proxy: {}", uri);
        ClientResponse response;
        try {
            response = webResource.uri(new URI(uri))
                    .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .get(ClientResponse.class);
        } catch (UniformInterfaceException e) {
            if (e.getResponse().getStatus() == ClientResponse.Status.NOT_FOUND.getStatusCode()) {
                return null;
            } else {
                throw new CoreException(e);
            }
        } catch (URISyntaxException e) {
            throw new CoreException(e);
        }
        return new DefaultDormResource(mavenUri.getFilename().getFilename(),
                mavenUri.getFilename().getExtension(),
                response.getEntity(File.class));
    }


    private MavenMetadata convertMetadata(DormMetadata dormMetadata) {
        if (!dormMetadata.getClass().equals(MavenMetadata.class)) {
            throw new CoreException("The metadata isn't a Maven metadata");
        }
        return (MavenMetadata) dormMetadata;
    }
}
