package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jStoreLabelTask extends Neo4jAbstractTask {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jStoreLabelTask.class);

    @Inject
    private DormMetadataLabel dormMetadataLabel;

    @Override
    public Void execute() {
        Set<DormMetadata> dormMetadataSet = dormMetadataLabel.getMetadatas();
        Neo4jLabel label = new Neo4jLabel(dormMetadataLabel.getLabel());
        if (isAlreadyExist(label)) {
            Neo4jResponse<Neo4jLabel> responseLabel = getLabel(label, true);
            List<Neo4jRelationship> toAddRelationships = findRelationshipsToAdd(dormMetadataSet, responseLabel);
            List<Neo4jRelationship> toDeleteRelationships = findRelationshipsToDelete(dormMetadataSet, responseLabel);
            neo4jService.createRelationships(toAddRelationships);
            neo4jService.deleteRelationships(toDeleteRelationships);
        } else {
            Neo4jResponse<Neo4jLabel> responseLabel = storeLabel(label);
            addMetadataRelationships(dormMetadataSet, responseLabel);
        }
        return null;
    }

    private List<Neo4jRelationship> findRelationshipsToDelete(Set<DormMetadata> dormMetadataSet, Neo4jResponse<Neo4jLabel> responseLabel) {
        List<Neo4jRelationship> toDeleteRelationships = new ArrayList<Neo4jRelationship>();
        for (Neo4jResponse<Neo4jMetadata> metadataResponse : responseLabel.getData().getMetadataResponseSet()) {
            boolean isExist = false;
            for (DormMetadata dormMetadata : dormMetadataSet) {
                if (convertToDormMetadata(metadataResponse).equals(dormMetadata)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                Neo4jRelationship neo4jRelationship = neo4jService.getRelationship(metadataResponse, Neo4jRelationship.LABEL_TYPE, Neo4jRelationship.Direction.IN);
                toDeleteRelationships.add(neo4jRelationship);
            }
        }
        return toDeleteRelationships;
    }

    private List<Neo4jRelationship> findRelationshipsToAdd(Set<DormMetadata> dormMetadataSet, Neo4jResponse<Neo4jLabel> responseLabel) {
        List<Neo4jRelationship> toAddRelationships = new ArrayList<Neo4jRelationship>();
        for (DormMetadata dormMetadata : dormMetadataSet) {
            boolean isExist = false;
            for (Neo4jResponse<Neo4jMetadata> metadataResponse : responseLabel.getData().getMetadataResponseSet()) {
                if (dormMetadata.equals(convertToDormMetadata(metadataResponse))) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                Neo4jResponse<Neo4jMetadata> metadataResponse = neo4jService.getNode(dormMetadata.getId(), Neo4jService.RESPONSE_METADATA);
                Neo4jRelationship relationship = new Neo4jRelationship(responseLabel.getCreate_relationship(),
                        metadataResponse.getSelf(), Neo4jRelationship.LABEL_TYPE);
                toAddRelationships.add(relationship);
            }
        }
        return toAddRelationships;
    }

    private void addMetadataRelationships(Set<DormMetadata> dormMetadataSet, Neo4jResponse<Neo4jLabel> responseLabel) {
        for (DormMetadata dormMetadata : dormMetadataSet) {
            Neo4jResponse<Neo4jMetadata> neo4jMetadata = getMetadata(dormMetadata);
            Neo4jRelationship neo4jRelationship = new Neo4jRelationship(responseLabel.getCreate_relationship(),
                    neo4jMetadata.getSelf(), Neo4jRelationship.LABEL_TYPE);
            neo4jService.createRelationship(neo4jRelationship);
        }
    }

    private boolean isAlreadyExist(Neo4jLabel label) {
        return getLabel(label, false) != null;
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
        Neo4jResponse<Neo4jLabel> labelNeo4jResponse = neo4jService.createNode(label);
        neo4jService.createIndex("name", label.getLabel(), labelNeo4jResponse.getSelf(), indexProvider.getLabelIndex());
        return labelNeo4jResponse;
    }
}
