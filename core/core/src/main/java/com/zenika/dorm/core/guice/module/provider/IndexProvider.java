package com.zenika.dorm.core.guice.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.neo4j.Neo4jAbstractTask;
import com.zenika.dorm.core.dao.neo4j.Neo4jIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Singleton
public class IndexProvider implements Provider<Neo4jIndex>{

    private static final Logger LOG = LoggerFactory.getLogger(IndexProvider.class);

    private WebResource resource;

    private Neo4jIndex index;

    @Inject
    public IndexProvider(WebResource resource){
        this.resource = resource;
        index = post(new Neo4jIndex());
    }

    @Override
    public Neo4jIndex get() {
        return index;
    }

    private Neo4jIndex post(Neo4jIndex index) {
        index = resource.path(Neo4jIndex.INDEX_PATH).accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).post(Neo4jIndex.class, index);
        LOG.info("POST to " + resource.getURI() + "/" + Neo4jIndex.INDEX_PATH);
        return index;
    }
}
