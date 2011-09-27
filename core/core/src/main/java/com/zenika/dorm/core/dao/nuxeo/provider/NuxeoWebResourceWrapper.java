package com.zenika.dorm.core.dao.nuxeo.provider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.dao.neo4j.Neo4jIndex;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import com.zenika.dorm.core.dao.neo4j.Neo4jResponse;
import com.zenika.dorm.core.dao.neo4j.util.ObjectMapperProvider;
import com.zenika.dorm.core.dao.nuxeo.DormDaoNuxeo;
import com.zenika.dorm.core.dao.nuxeo.NuxeoMetadata;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.http.auth.AuthScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoWebResourceWrapper {

    private static final Logger LOG = LoggerFactory.getLogger(NuxeoWebResourceWrapper.class);

    private WebResource resource;

    private boolean enableProxy = false;
    private String hostProxy = "192.168.0.24";
    private String portProxy = "8008";

    public NuxeoWebResourceWrapper() {
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        if (enableProxy) {
            config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_PROXY_URI, "http://" + hostProxy + ":" + portProxy);
        }
        config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_INTERACTIVE, true);
        config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_CREDENTIALS_PROVIDER, new NuxeoCredential());
        config.getClasses().addAll(getClasses());
        Client client = ApacheHttpClient.create(config);
        resource = client.resource(DormDaoNuxeo.DATA_ENTRY_POINT_URI);
    }

    public WebResource get() {
        return resource;
    }

    public Collection<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(NuxeoMetadata.class);
        return classes;
    }

    private static class NuxeoCredential implements CredentialsProvider {

        @Override
        public Credentials getCredentials(AuthScheme scheme, String host, int port, boolean proxy) throws CredentialsNotAvailableException {
            Credentials credentials = new UsernamePasswordCredentials("admin", "admin");
            return credentials;
        }
    }
}