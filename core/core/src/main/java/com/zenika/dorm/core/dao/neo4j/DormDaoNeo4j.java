package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jWebResourceWrapper;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormDaoNeo4j implements DormDao {

    public static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";

    @Inject
    private Neo4jWebResourceWrapper wrapper;
//    @Inject
//    private Neo4jIndex index;
    @Inject
    private ExtensionFactoryServiceLoader serviceLoader;

    @Override
    public DormMetadata get(final DormBasicQuery query) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jWebResourceWrapper.class).toInstance(wrapper);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormBasicQuery.class).toInstance(query);
            }
        }).getInstance(Neo4jGetTask.class).execute();
    }

    @Override
    public List<DormMetadata> getMetadataByExtension(String extensionName, Map<String, String> extensionClauses, Usage usage) {
        throw new UnsupportedOperationException("Not implement yet");
    }

    @Override
    public void saveOrUpdateMetadata(final DormMetadata metadata) {
        Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jWebResourceWrapper.class).toInstance(wrapper);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormMetadata.class).toInstance(metadata);
            }
        }).getInstance(Neo4jSinglePushTask.class).execute();
    }

    @Override
    public DependencyNode addDependenciesToNode(final DependencyNode root) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jWebResourceWrapper.class).toInstance(wrapper);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DependencyNode.class).toInstance(root);
            }
        }).getInstance(Neo4jAddDependenciesTask.class).execute();
    }
}