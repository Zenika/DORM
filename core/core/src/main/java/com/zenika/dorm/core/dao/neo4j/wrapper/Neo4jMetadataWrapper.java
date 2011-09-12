package com.zenika.dorm.core.dao.neo4j.wrapper;

import com.zenika.dorm.core.dao.neo4j.mixin.Neo4jMetadataMixIn;
import com.zenika.dorm.core.model.DormMetadata;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Neo4jMetadataWrapper implements Neo4jWrapper {

    private DormMetadata metadata;

    public Neo4jMetadataWrapper(DormMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public void addMappingConfig(ObjectMapper mapper) {
        mapper.getSerializationConfig().addMixInAnnotations(DormMetadata.class, Neo4jMetadataMixIn.class);
    }

    public DormMetadata getMetadata() {
        return metadata;
    }
}
