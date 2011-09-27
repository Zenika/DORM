package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jWebResourceWrapper;
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

    public static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";

    @Inject
    private Neo4jWebResourceWrapper wrapper;
    @Inject
    private Neo4jIndex index;
    @Inject
    private ExtensionFactoryServiceLoader serviceLoader;

    @Override
    public DormMetadata getMetadataByFunctionalId(final String functionalId) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jWebResourceWrapper.class).toInstance(wrapper);
                bind(Neo4jIndex.class).toInstance(index);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(String.class).toInstance(functionalId);
            }
        }).getInstance(Neo4jRetrieveByFunctionalId.class).execute();
    }

    @Override
    public List<DormMetadata> getMetadataByExtension(String extensionName, Map<String, String> extensionClauses, Usage usage) {
        throw new UnsupportedOperationException("Not implement yet");
    }

    @Override
    public void saveMetadata(final DormMetadata metadata) {
        Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jWebResourceWrapper.class).toInstance(wrapper);
                bind(Neo4jIndex.class).toInstance(index);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormMetadata.class).toInstance(metadata);
            }
        }).getInstance(Neo4jSinglePushTask.class).execute();
    }
}