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

    @Override
    public DormMetadata execute() {
        DormBasicQuery query = new DormBasicQuery.Builder()
                .extensionName(this.metadata.getType())
                .name(this.metadata.getName())
                .version(this.metadata.getVersion())
                .build();
        Neo4jResponse response = getMetadata(query);
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
            Neo4jResponse metadataResponse = neo4jService.createNode(metadata);
            id = extractId(metadataResponse.getSelf());
            return factory.fromMap(id, properties);
        } else {
            LOG.info("The metadata already exist");
        }
        return null;
    }

}
