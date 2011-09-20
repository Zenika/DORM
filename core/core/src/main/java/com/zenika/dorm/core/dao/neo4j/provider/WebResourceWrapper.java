package com.zenika.dorm.core.dao.neo4j.provider;

import com.google.inject.Singleton;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Singleton
public class WebResourceWrapper {

    private static final Logger LOG = LoggerFactory.getLogger(WebResourceWrapper.class);

    private WebResource resource;

//    @Inject
//    private Collection<Class<?>> classes;

    public WebResourceWrapper() {
        DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getClasses().addAll(getClasses());
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        Client client = ApacheHttpClient.create(config);
        resource = client.resource(DormDaoNeo4j.DATA_ENTRY_POINT_URI);
    }

    public WebResource get() {
        return resource;
    }

    public Collection<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(ObjectMapperProvider.class);
        classes.add(Neo4jMetadata.class);
        classes.add(Neo4jResponse.class);
        classes.add(Neo4jIndex.class);
        return classes;
    }

}
