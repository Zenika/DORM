package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jWebResourceWrapper;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
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

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jAbstractTask.class.getName());

    protected static final String GREMLIN_URI = "http://localhost:7474/db/data/ext/GremlinPlugin/graphdb/execute_script";
    protected static final String NODE_URI = "http://localhost:7474/db/data/node/";
    protected static final String NODE_PATH = "node";

    protected static final GenericType<List<Neo4jResponse<Neo4jMetadata>>> LIST_METADATA_GENERIC_TYPE =
            new GenericType<List<Neo4jResponse<Neo4jMetadata>>>() {
            };
    protected static final GenericType<List<Neo4jRelationship>> LIST_RELATIONSHIP_GENERIC_TYPE =
            new GenericType<List<Neo4jRelationship>>() {
            };
    protected static final GenericType<Neo4jResponse<Neo4jLabel>> RESPONSE_LABEL =
            new GenericType<Neo4jResponse<Neo4jLabel>>() {
            };

    @Inject
    protected Neo4jWebResourceWrapper wrapper;
    @Inject
    protected Neo4jIndex index;
    @Inject
    protected ExtensionFactoryServiceLoader serviceLoader;

    protected static void logRequest(String type, WebResource resource, String path) {
        LOG.info(type + " to " + resource.getURI() + "/" + path);
    }

    protected static void logRequest(String type, URI uri) {
        LOG.info(type + " to " + uri);
    }

    protected static void logRequest(String type, String uri) {
        LOG.info(type + " to " + uri);
    }

    protected Neo4jAbstractTask() {

    }

    public abstract Object execute();

    protected Neo4jResponse<Neo4jMetadata> getMetadata(DormBasicQuery query) {
        try {

            URI gremlinUri = new URI(GREMLIN_URI);

            String gremlinScript = buildScript(query);

            LOG.info("Script: " + gremlinScript);

            List<Neo4jResponse<Neo4jMetadata>> metadataResponses = wrapper.get().uri(gremlinUri)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(gremlinScript)
                    .post(LIST_METADATA_GENERIC_TYPE);

//            ClientResponse responses = wrapper.get().uri(gremlinUri)
//                    .accept(MediaType.APPLICATION_JSON_TYPE)
//                    .type(MediaType.APPLICATION_JSON_TYPE)
//                    .entity(gremlinScript)
//                    .post(ClientResponse.class);
//
//            LOG.info("Response: " + responses.getEntity(String.class));

            if (metadataResponses.size() > 1) {
                throw new CoreException("Retrieved multiple result");
            }

            logRequest("GET", gremlinUri);

            if (metadataResponses.size() == 0) {
                return null;
            }
            return metadataResponses.get(0);
//            return  null;
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
    }

    private String buildScript(DormBasicQuery query) {
        return new StringBuilder(50)
                .append("{\"script\": \"g.V.filter{it.extensionName == \\\"")
                .append(query.getExtensionName())
                .append("\\\" && it.name == \\\"")
                .append(query.getName())
                .append("\\\" && it.version == \\\"")
                .append(query.getVersion())
                .append("\\\"}\"}")
                .toString();
    }

    protected Long extractId(String uri) {
        String[] split = uri.split("/");
        return Long.valueOf(split[split.length - 1]);
    }

    protected String generateNodeUri(Long id) {
        return NODE_URI + id;
    }

    protected String generateCreateRelationshipUri(Long id) {
        return generateNodeUri(id) + "/relationships";
    }

    protected void createRelationships(Neo4jRelationship relationship) {
        try {
            URI createRelationship = new URI(relationship.getFrom());

            ClientResponse clientResponse = wrapper.get().uri(createRelationship)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(relationship)
                    .post(ClientResponse.class);

            logRequest("POST", clientResponse.getEntity(String.class));
            LOG.debug("Uri: {}", createRelationship);
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
    }
}
