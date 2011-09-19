package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jSinglePushService {

    @Inject
    private DormMetadata metadata;


    public Void execute() {
//        String identifier = metadata.getIdentifier();
        String version = metadata.getVersion();
        String extensionName = metadata.getExtensionName();

        
        return null;
    }
}
