package com.zenika.dorm.artifactoryimporter;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
import org.apache.commons.httpclient.auth.CredentialsProvider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ArtifactoryWebResource {

    private WebResource resource;

    private boolean enableProxy = false;
    private String hostProxy = "192.168.0.24";
    private String portProxy = "8008";

    public ArtifactoryWebResource() {
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        if (enableProxy){
            config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_PROXY_URI, "http://" + hostProxy + ":" + portProxy);
        }
        config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_INTERACTIVE, true);
        config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_CREDENTIALS_PROVIDER, new ArtifactoryCredential());
        config.getClasses().addAll(getClasses());
        Client client = ApacheHttpClient.create(config);
        resource = client.resource(ArtifactoryImporter.ARTIFACTORY_ENTRY_POINT);
    }

    public Collection<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(ArtifactoryProvider.class);
        classes.add(ArtifactoryFile.class);
        classes.add(ArtifactoryImporter.class);
        return classes;
    }

    public WebResource getResource() {
        return resource;
    }

    private class ArtifactoryCredential implements CredentialsProvider {

        @Override
        public Credentials getCredentials(AuthScheme authScheme, String s, int i, boolean b) throws CredentialsNotAvailableException {
            Credentials credentials = new UsernamePasswordCredentials("admin", "password");
            return credentials;
        }
    }
}
