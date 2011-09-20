package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.apache.commons.logging.Log;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jSinglePushTask extends Neo4jAbstractTask {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jSinglePushTask.class);

    private static final String NODE_PATH = "node";

    @Inject
    private DormMetadata metadata;

    @Override
    public Void execute() {
        Neo4jMetadata metadata = new Neo4jMetadata(
                this.metadata.getQualifier(),
                this.metadata.getExtensionName(),
                serviceLoader.getInstanceOf(this.metadata.getExtensionName()).toMap(this.metadata)
        );

        Neo4jResponse metadataResponse = createNode(metadata);
        Neo4jResponse propertiesResponse = createNode(metadata.getProperties());

        createRelationships(
                new Neo4jRelationship(
                        metadataResponse.getCreate_relationship(),
                        propertiesResponse.getSelf(),
                        Neo4jMetadata.PROPERTIES_RELATIONSHIPS
                )
        );

        createIndex(metadataResponse, metadata.getQualifier());
        return null;
    }

    private Neo4jResponse createNode(Object node) {

        Neo4jResponse response = resource.path(NODE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .entity(node)
                .post(Neo4jResponse.class);

        logRequest("POST", resource, NODE_PATH);
        LOG.debug("Response self: " + response.getSelf());
        return response;
    }

    private void createRelationships(Neo4jRelationship relationship) {
        try {
            URI createRelationship = new URI(relationship.getFrom());

            resource.uri(createRelationship)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(relationship)
                    .post();

            logRequest("POST", createRelationship);
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
    }

    private void createIndex(Neo4jResponse response, String value) {
        try {
            URI indexUri = new URI(
                    index.getTemplate()
                            .replace("{key}", Neo4jIndex.INDEX_DEFAULT_KEY)
                            .replace("{value}", value)
            );

            String nodeUri = new StringBuilder(50)
                    .append("\"")
                    .append(response.getSelf())
                    .append("\"")
                    .toString();

            resource.uri(indexUri)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(nodeUri)
                    .post();

            logRequest("POST", indexUri);
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
    }
}
