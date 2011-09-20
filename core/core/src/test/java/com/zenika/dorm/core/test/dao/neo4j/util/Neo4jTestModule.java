package com.zenika.dorm.core.test.dao.neo4j.util;

import com.google.guiceberry.GuiceBerryModule;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.neo4j.Neo4jIndex;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import com.zenika.dorm.core.dao.neo4j.Neo4jResponse;
import com.zenika.dorm.core.guice.module.provider.IndexProvider;
import com.zenika.dorm.core.guice.module.provider.WebClientProvider;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jTestModule extends GuiceBerryModule {

    @Override
    protected void configure() {
        super.configure();

        bind(WebResource.class).toProvider(WebClientProvider.class);

        requireBinding(WebResource.class);

        bind(Neo4jIndex.class).toProvider(IndexProvider.class);
        bind(ExtensionFactoryServiceLoader.class);
    }
}
