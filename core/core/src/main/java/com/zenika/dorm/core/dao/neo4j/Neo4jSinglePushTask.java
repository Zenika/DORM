package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jSinglePushTask extends Neo4jAbstractTask {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jSinglePushTask.class);

    @Inject
    private DormMetadata metadata;

    private WebResource resource;

    @Override
    public DormMetadata execute() {
        resource = wrapper.get();

        DormBasicQuery query = new DormBasicQuery.Builder()
                .extensionName(this.metadata.getType())
                .name(this.metadata.getName())
                .version(this.metadata.getVersion())
                .build();

        Neo4jResponse response = getMetadata(query);
//        Neo4jResponse response = null;

        if (response == null) {

            Long id;
            ExtensionMetadataFactory factory = serviceLoader.getInstanceOf(metadata.getType());
            Map<String, String> properties = factory.toMap(this.metadata);

            Neo4jMetadata metadata = new Neo4jMetadata(
                    this.metadata.getType(),
                    this.metadata.getName(),
                    this.metadata.getVersion(),
                    properties
            );

            Neo4jResponse metadataResponse = createNode(metadata);

            id = extractId(metadataResponse.getSelf());

//            Neo4jResponse propertiesResponse = createNode(metadata.getProperties());
//
//            createRelationships(
//                    new Neo4jRelationship(
//                            metadataResponse.getCreate_relationship(),
//                            propertiesResponse.getSelf(),
//                            Neo4jMetadata.PROPERTIES_RELATIONSHIPS
//                    )
//            );


            // Create index is useless for the moment
//            createIndex(metadataResponse, this.metadata.getFunctionalId());

            return factory.fromMap(id, properties);
        } else {
            LOG.info("The metadata already exist");
        }
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

    /**
     * @param response
     * @param value
     * @deprecated index is useless for the moment
     */
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

            LOG.info("NodeUri: " + nodeUri);
            LOG.info("Index uri: " + indexUri);

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
