package com.zenika.dorm.core.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.dao.neo4j.Neo4jIndex;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import com.zenika.dorm.core.dao.neo4j.Neo4jResponse;
import com.zenika.dorm.core.dao.neo4j.util.ObjectMapperProvider;
import com.zenika.dorm.core.guice.module.provider.IndexProvider;
import com.zenika.dorm.core.guice.module.provider.WebClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormCoreNeo4jModule extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(DormCoreNeo4jModule.class);

//    @Provides
//    public Collection<Class<?>> getClasses() {
//        final Set<Class<?>> classes = new HashSet<Class<?>>();
//        classes.add(Neo4jMetadata.class);
//        classes.add(Neo4jResponse.class);
//        classes.add(Neo4jIndex.class);
//        return classes;
//    }

    @Override
    protected void configure() {

        if (LOG.isInfoEnabled()) {
            LOG.info("Configure dorm neo4j guice module");
        }

        bind(WebResource.class).toProvider(WebClientProvider.class);

        requireBinding(WebResource.class);

        bind(Neo4jIndex.class).toProvider(IndexProvider.class);
        bind(DormDao.class).to(DormDaoNeo4j.class);
    }
}
