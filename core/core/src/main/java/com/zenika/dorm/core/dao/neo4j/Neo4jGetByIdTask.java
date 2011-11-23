package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jGetByIdTask extends Neo4jAbstractTask {

    @Inject
    private Long id;

    @Override
    public DormMetadata execute() {
        Neo4jResponse<Neo4jMetadata> neo4jMetadataNeo4jResponse = neo4jService.getNode(id, Neo4jService.RESPONSE_METADATA);
        if (neo4jMetadataNeo4jResponse.getData().getName() == null) {
            return null;
        } else {
            Neo4jMetadata neo4jMetadata = neo4jMetadataNeo4jResponse.getData();
            return serviceLoader.getInstanceOf(neo4jMetadata.getExtensionName())
                    .fromMap(id, neo4jMetadata.getProperties());
        }
    }
}
