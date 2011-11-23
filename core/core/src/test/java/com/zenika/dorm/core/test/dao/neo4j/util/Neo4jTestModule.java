package com.zenika.dorm.core.test.dao.neo4j.util;

import com.google.inject.AbstractModule;
import com.zenika.dorm.core.dao.neo4j.Neo4jIndex;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jIndexProvider;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jWebResourceWrapper;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jTestModule extends AbstractModule {

    @Override
    protected void configure() {

        requireBinding(Neo4jWebResourceWrapper.class);

    }
}
