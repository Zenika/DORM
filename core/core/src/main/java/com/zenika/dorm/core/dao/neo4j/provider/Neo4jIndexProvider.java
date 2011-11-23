package com.zenika.dorm.core.dao.neo4j.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.neo4j.Neo4jIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Singleton
public class Neo4jIndexProvider {

    private WebResource resource;

    private Neo4jIndex metadataIndex;
    private Neo4jIndex labelIndex;

    @Inject
    public Neo4jIndexProvider(Neo4jWebResourceWrapper wrapper) {
        this.resource = wrapper.get();
        configure();
    }

    private void configure() {
        labelIndex = new Neo4jIndex();
        labelIndex.setName(Neo4jIndex.LABEL_INDEX_NAME);
        labelIndex = saveIndex(labelIndex);

        metadataIndex = new Neo4jIndex();
        metadataIndex.setName(Neo4jIndex.METADATA_INDEX_NAME);
        metadataIndex = saveIndex(metadataIndex);
    }

    private Neo4jIndex saveIndex(Neo4jIndex labelIndex) {
        return resource.path(Neo4jIndex.INDEX_PATH)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(labelIndex)
                .post(Neo4jIndex.class);
    }

    public Neo4jIndex getMetadataIndex() {
        return metadataIndex;
    }

    public Neo4jIndex getLabelIndex() {
        return labelIndex;
    }
}
