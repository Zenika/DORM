package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jIndexProvider;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jWebResourceWrapper;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import javax.annotation.PostConstruct;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormDaoNeo4j implements DormDao {

    public static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";

    @Inject
    private ExtensionFactoryServiceLoader serviceLoader;
    @Inject
    private Neo4jIndexProvider indexProvider;
    @Inject
    private Neo4jService service;

    @Override
    public DormMetadata get(final DormBasicQuery query) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jIndexProvider.class).toInstance(indexProvider);
                bind(Neo4jService.class).toInstance(service);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormBasicQuery.class).toInstance(query);
            }
        }).getInstance(Neo4jGetTask.class).execute();
    }

    @Override
    public void saveOrUpdateMetadata(final DormMetadata metadata) {
        Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jIndexProvider.class).toInstance(indexProvider);
                bind(Neo4jService.class).toInstance(service);
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
                bind(Neo4jIndexProvider.class).toInstance(indexProvider);
                bind(Neo4jService.class).toInstance(service);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DependencyNode.class).toInstance(root);
            }
        }).getInstance(Neo4jAddDependenciesTask.class).execute();
    }

    @Override
    public DormMetadataLabel createOrUpdateLabel(final DormMetadataLabel metadataLabel) {
        Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jIndexProvider.class).toInstance(indexProvider);
                bind(Neo4jService.class).toInstance(service);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormMetadataLabel.class).toInstance(metadataLabel);
            }
        }).getInstance(Neo4jStoreLabelTask.class).execute();
        return null;
    }

    @Override
    public DormMetadata getById(long artifactId, String extensionName) {
        return null;
    }

    @Override
    public DormMetadata getById(final long artifactId) {
        DormMetadata dormMetadata = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jIndexProvider.class).toInstance(indexProvider);
                bind(Neo4jService.class).toInstance(service);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(Long.class).toInstance(new Long(artifactId));
            }
        }).getInstance(Neo4jGetByIdTask.class).execute();
        return dormMetadata;
    }

    @Override
    public DormMetadataLabel getByLabel(final DormMetadataLabel label) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jIndexProvider.class).toInstance(indexProvider);
                bind(Neo4jService.class).toInstance(service);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormMetadataLabel.class).toInstance(label);
            }
        }).getInstance(Neo4jGetByLabelTask.class).execute();
    }
}