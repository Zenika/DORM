package com.zenika.dorm.core.dao.neo4j.mixin;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class Neo4jMetadataMixIn implements DormMetadata {

    @JsonIgnore
    @Override
    public abstract DormMetadataExtension getExtension();
}