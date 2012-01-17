package com.zenika.dorm.maven.provider;

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
import com.zenika.dorm.maven.model.MavenRemoteRepository;
import com.zenika.dorm.maven.service.MavenProxyServiceHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ProxyWebResourceWrapper {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyWebResourceWrapper.class);

    private boolean enableProxy = false;
    private String hostProxy = "192.168.182.1";
    private String portProxy = "8008";

    private WebResource resource;

    public ProxyWebResourceWrapper() {
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        if (enableProxy) {
            config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_PROXY_URI, "http://" + hostProxy + ":" + portProxy);
        }
        config.getClasses().addAll(getClasses());
        Client client = ApacheHttpClient.create(config);
        resource = client.resource(MavenRemoteRepository.DEFAULT_URL);
    }

    public WebResource get() {
        return resource;
    }

    public Collection<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        return classes;
    }
}