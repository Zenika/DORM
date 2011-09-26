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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoWebResourceWrapper {

    private static final Logger LOG = LoggerFactory.getLogger(NuxeoWebResourceWrapper.class);

    private WebResource resource;

    private boolean enableProxy = true;
    private String hostProxy = "192.168.0.24";
    private String portProxy = "8008";

//    @Inject
//    private Collection<Class<?>> classes;

    public NuxeoWebResourceWrapper() {
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        if (enableProxy) {
            config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_PROXY_URI, "http://" + hostProxy + ":" + portProxy);
        }
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
}
