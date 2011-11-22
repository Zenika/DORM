package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;

import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jStoreLabelTask extends Neo4jAbstractTask {

    @Inject
    private DormMetadataLabel dormMetadataLabel;

    private WebResource resource;

    @Override
    public Void execute() {
        resource = wrapper.get();
        Set<DormMetadata> dormMetadataSet = dormMetadataLabel.getMetadatas();
        Neo4jLabel label = new Neo4jLabel(dormMetadataLabel.getLabel());
        Neo4jResponse<Neo4jLabel> responseLabel = storeLabel(label);
        for (DormMetadata dormMetadata : dormMetadataSet) {
            Neo4jResponse<Neo4jMetadata> neo4jMetadata = getMetadata(dormMetadata);
            Neo4jRelationship neo4jRelationship = new Neo4jRelationship(responseLabel.getCreate_relationship(),
                    neo4jMetadata.getSelf(), Neo4jRelationship.LABEL_TYPE);
            createRelationships(neo4jRelationship);
        }
        return null;
    }

    private Neo4jResponse<Neo4jMetadata> getMetadata(DormMetadata dormMetadata) {
        DormBasicQuery dormBasicQuery = new DormBasicQuery.Builder()
                .extensionName(dormMetadata.getType())
                .name(dormMetadata.getName())
                .version(dormMetadata.getVersion())
                .build();
        return getMetadata(dormBasicQuery);
    }

    private Neo4jResponse<Neo4jLabel> storeLabel(Neo4jLabel label) {
        return resource.path(NODE_PATH)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(label)
                .post(RESPONSE_LABEL);
    }

}
