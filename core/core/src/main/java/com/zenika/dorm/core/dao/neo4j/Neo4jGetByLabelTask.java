package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jGetByLabelTask extends Neo4jAbstractTask{

    @Inject
    private DormMetadataLabel dormMetadataLabel;

    @Override
    public DormMetadataLabel execute() {
        Neo4jLabel label = new Neo4jLabel(dormMetadataLabel.getLabel());
        Neo4jResponse<Neo4jLabel> responseLabel = getLabel(label, true);
        for (Neo4jResponse<Neo4jMetadata> metadata : responseLabel.getData().getMetadataResponseSet()){
            dormMetadataLabel.addMetadata(convertToDormMetadata(metadata));
        }
        return dormMetadataLabel;
    }
}