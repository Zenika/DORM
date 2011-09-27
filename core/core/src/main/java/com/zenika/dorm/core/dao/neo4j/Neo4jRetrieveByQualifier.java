package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jRetrieveByQualifier extends Neo4jAbstractTask {

    @Inject
    private String qualifier;

    private WebResource resource;

    @Override
    public DormMetadata execute() {
        resource = wrapper.get();

        Neo4jResponse<Neo4jMetadata> metadataResponse = getMetadata(qualifier);
        Neo4jRelationship relationship = getOutgoingRelationship(metadataResponse, Neo4jMetadata.PROPERTIES_RELATIONSHIPS);
        Neo4jResponse<Map<String, String>> propertiesResponse = getProperties(relationship.getEnd());

        Neo4jMetadata metadata = metadataResponse.getData();

        return serviceLoader.getInstanceOf(metadata.getExtensionName())
                .fromMap(id, propertiesResponse.getData());
    }

    private Neo4jResponse<Map<String, String>> getProperties(String propertiesStrUri) {
        try {
            URI propertiesUri = new URI(propertiesStrUri);

            Neo4jResponse<Map<String, String>> propertiesResponse = resource.uri(propertiesUri)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get(PROPERTIES_GENERIC_TYPE);

            return propertiesResponse;
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
    }

    private Neo4jRelationship getOutgoingRelationship(Neo4jResponse metadataResponse, String propertiesRelationships) {
        try {
            URI relationshipUri = new URI(metadataResponse.getOutgoing_typed_relationships()
                    .replace("{-list|&|types}", propertiesRelationships)
            );

            List<Neo4jRelationship> relationships = resource.uri(relationshipUri)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get(LIST_RELATIONSHIP_GENERIC_TYPE);

            if (relationships.size() > 1) {
                throw new CoreException("Retrieved multiple result");
            }

            return relationships.get(0);
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
    }
}
