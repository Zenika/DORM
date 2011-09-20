package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormDaoNeo4j implements DormDao {

    private static final Logger LOG = LoggerFactory.getLogger(DormDaoNeo4j.class);

    public static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";

    @Inject
    private WebResource resource;
    @Inject
    private Neo4jIndex index;
    @Inject
    private ExtensionFactoryServiceLoader serviceLoader;

    @Override
    public DormMetadata getMetadataByQualifier(final String qualifier) {
        return null;
    }

    @Override
    public List<DormMetadata> getMetadataByExtension(String extensionName, Map<String, String> extensionClauses, Usage usage) {
        return null;
    }

    @Override
    public void saveMetadata(final DormMetadata metadata) {
        Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(WebResource.class).toInstance(resource);
                bind(Neo4jIndex.class).toInstance(index);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormMetadata.class).toInstance(metadata);
            }
        }).getInstance(Neo4jSinglePushTask.class).execute();
    }
}