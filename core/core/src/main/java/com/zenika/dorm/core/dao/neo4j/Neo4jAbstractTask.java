package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public abstract class Neo4jAbstractTask {

    private static Logger logger = LoggerFactory.getLogger(Neo4jAbstractTask.class.getName());

    public static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";
    public static final String NODE_PATH = "node";

    @Inject
    protected WebResource resource;
    @Inject
    protected Neo4jIndex index;
    @Inject
    protected ExtensionFactoryServiceLoader serviceLoader;

    protected static void logRequest(String type, WebResource resource, String path) {
        logger.info(type + " to " + resource.getURI() + "/" + path);
    }

    protected static void logRequest(String type, URI uri) {
        logger.info(type + " to " + uri);
    }

    protected static void logRequest(String type, String uri) {
        logger.info(type + " to " + uri);
    }

    public abstract Object execute();
}
