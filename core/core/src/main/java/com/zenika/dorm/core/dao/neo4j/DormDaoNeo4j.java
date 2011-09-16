package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;

import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormDaoNeo4j implements DormDao {

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
        Neo4jSinglePushService neo4jSinglePushService = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(DormMetadata.class).toInstance(metadata);
                    }
                }
        ).getInstance(Neo4jSinglePushService.class);
        neo4jSinglePushService.execute();
    }
}
