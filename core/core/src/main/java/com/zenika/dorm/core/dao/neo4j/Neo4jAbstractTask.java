package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jWebResourceWrapper;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public abstract class Neo4jAbstractTask {

    private static Logger logger = LoggerFactory.getLogger(Neo4jAbstractTask.class.getName());

    public static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";
    public static final String NODE_PATH = "node";

    protected static final GenericType<List<Neo4jResponse<Neo4jMetadata>>> LIST_METADATA_GENERIC_TYPE =
            new GenericType<List<Neo4jResponse<Neo4jMetadata>>>() {
            };
    protected static final GenericType<List<Neo4jRelationship>> LIST_RELATIONSHIP_GENERIC_TYPE =
            new GenericType<List<Neo4jRelationship>>() {
            };
    protected static final GenericType<Neo4jResponse<Map<String, String>>> PROPERTIES_GENERIC_TYPE =
            new GenericType<Neo4jResponse<Map<String, String>>>() {
            };

    @Inject
    protected Neo4jWebResourceWrapper wrapper;
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

    protected Neo4jResponse<Neo4jMetadata> getMetadata(String qualifier) {
        try {
            URI indexUri = new URI(index.getTemplate().replace("{key}", Neo4jIndex.INDEX_DEFAULT_KEY)
                    .replace("{value}", qualifier));

            List<Neo4jResponse<Neo4jMetadata>> metadataResponses = wrapper.get().uri(indexUri)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get(LIST_METADATA_GENERIC_TYPE);

            if (metadataResponses.size() > 1) {
                throw new CoreException("Retrieved multiple result");
            }

            logRequest("GET", indexUri);

            if (metadataResponses.size() == 0){
                return null;
            }
            return metadataResponses.get(0);
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
    }

}
