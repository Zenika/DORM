package com.zenika.dorm.maven.importer;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.Response.Status;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenRepositoryImporter {

    private static final Logger LOG = LoggerFactory.getLogger(MavenRepositoryImporter.class);

    private String baseUrl;
    private String host;
    private String port;

    private URI serverUri;

    private MavenXpp3Reader reader;

    private WebResource resource;

    public MavenRepositoryImporter(String baseUrl, String host, String port, String path) {
        this.baseUrl = baseUrl;
        serverUri = UriBuilder.fromUri(host).port(Integer.parseInt(port)).path(path).build();
        init();
    }

    public MavenRepositoryImporter(String baseRepository, URI serverUri) {
        this.baseUrl = baseRepository;
        this.serverUri = serverUri;
    }

    private void init() {
        Client client = new Client();
        resource = client.resource(serverUri);
        reader = new MavenXpp3Reader();
    }

    public void start() {
        sendPoms(new File(baseUrl));
    }

    public void sendPoms(File root) {
        LOG.trace("Root file : " + root);
        LOG.trace("List files : " + root.listFiles().length);
        for (File file : root.listFiles()) {
            if (FilenameUtils.getExtension(file.getName()).equals("pom")) {
                Model model = getPomModel(file);
                File[] files = getArtifactFile(file.getParentFile());
                if (model != null) {
                    sendFiles(files, model);
                }
            } else if (file.isDirectory()) {
                sendPoms(file);
            }
        }
    }

    public Model getPomModel(File pom) {
        try {
            InputStream stream = new FileInputStream(pom);
            Model model = reader.read(stream);
            stream.close();
            return model;
        } catch (FileNotFoundException e) {
            LOG.error("File not found");
        } catch (XmlPullParserException e) {
            LOG.error("Bad pom xml", e);
        } catch (IOException e) {
            LOG.error("IO Exception", e);
        }
        return null;
    }

    public File[] getArtifactFile(File base) {
        return base.listFiles();
    }

    public void sendFiles(File[] files, Model model) {
        for (File file : files) {
            LOG.trace("File is empty : " + file.length());
            try {
                resource.path(getArtifactPath(model, file.getName()))
                        .type(MediaType.APPLICATION_OCTET_STREAM)
                        .entity(file)
                        .put();
                LOG.info("Put request send to " + resource.getURI() + "/" + getArtifactPath(model, file.getName()));
            } catch (UniformInterfaceException e) {
                ClientResponse response = e.getResponse();
                if (response != null && Response.Status.fromStatusCode(response.getStatus()) != null) {
                    switch (Response.Status.fromStatusCode(response.getStatus())) {
                        case NOT_FOUND:
                            LOG.error("NOT FOUND at : " + response.getLocation());
                        case BAD_REQUEST:
                            LOG.error("BAD REQUEST : " + response.getLocation());
                        default:
                            LOG.error("Request error : " + response.getStatus() + " at : " + response.getLocation(), e);
                    }
                } else {
                    LOG.error("Request error", e);
                }
            } catch (ClientHandlerException e) {
                if (e.getCause().equals(ConnectException.class)) {
                    LOG.error("Can't not connect to the host connection refused", e);
                } else {
                    LOG.error("Connection Exception", e);
                }
            }
        }
    }

    public String getArtifactPath(Model model, String fileName) {
        StringBuilder sb = new StringBuilder();
        if (!(model.getGroupId() == null || model.getGroupId().isEmpty())) {
            sb.append(model.getGroupId().replace('.', '/'));
            sb.append('/');
        }
        if (!(model.getArtifactId() == null || model.getArtifactId().isEmpty())) {
            sb.append(model.getArtifactId());
            sb.append('/');
        }
        if (!(model.getVersion() == null || model.getVersion().isEmpty())) {
            sb.append(model.getVersion());
            sb.append('/');
        }
        sb.append(fileName);
        return sb.toString();
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
