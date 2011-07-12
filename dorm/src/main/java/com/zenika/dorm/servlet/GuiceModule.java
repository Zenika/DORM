package com.zenika.dorm.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.TypeLiteral;
import com.mongodb.Mongo;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.mongo.DormDaoMongo;
import com.zenika.dorm.core.dao.mongo.MongoInstance;
import com.zenika.dorm.core.model.old.MetadataExtension;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.impl.DormServiceImpl;
import com.zenika.dorm.core.ws.resource.DormResource;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;
import com.zenika.dorm.maven.service.MavenService;
import com.zenika.dorm.maven.service.impl.MavenServiceImpl;
import com.zenika.dorm.maven.ws.resource.MavenResource;

import java.net.UnknownHostException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class GuiceModule extends JerseyServletModule {

    // TODO: Externalize this
    private static final String HOST = "localhost";
    private static final String DATABASE = "dorm";
    private static final String ARTIFACTS_COLLECTION = "artifacts";

    @Override
    protected void configureServlets() {

        // jax rs resources
        bind(DormResource.class);
        bind(MavenResource.class);

        bindDAO();
        bindServices();

        serve("/*").with(GuiceContainer.class);
    }

    private void bindDAO() {

//        MongoProxy mongoProxy = Guice.createInjector(new AbstractModule() {
//
//            @Override
//            protected void configure() {
//
//                Mongo mongo;
//                try {
//                    mongo = new Mongo("localhost");
//                } catch (UnknownHostException e) {
//                    throw new RuntimeException("Unable to connect to mongo db at " + HOST);
//                }
//
//                Morphia morphia = new Morphia();
//
//                bind(Mongo.class).toInstance(mongo);
//                bind(Morphia.class).toInstance(morphia);
//                bind(String.class).toInstance(DATABASE);
//            }
//        }).getInstance(MongoProxy.class);

        MongoInstance mongoInstance = Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {

                Mongo mongo;
                try {
                    mongo = new Mongo("localhost");
                } catch (UnknownHostException e) {
                    throw new RuntimeException("Unable to connect to mongo db at " + HOST);
                }

                bind(Mongo.class).toInstance(mongo);
                bindConstant().annotatedWith(MongoInstance.MongoDatabase.class).to(DATABASE);
                bindConstant().annotatedWith(MongoInstance.ArtifactsCollection.class).to(ARTIFACTS_COLLECTION);

            }
        }).getInstance(MongoInstance.class);

        bind(MongoInstance.class).toInstance(mongoInstance);

        bind(new TypeLiteral<DormDao<MetadataExtension>>() {}).to(new TypeLiteral<DormDaoMongo<MetadataExtension>>() {});
        bind(new TypeLiteral<DormDao<DormMavenMetadata>>() {}).to(new TypeLiteral<DormDaoMongo<DormMavenMetadata>>() {});
    }

    private void bindServices() {

        // dorm service
        bind(new TypeLiteral<DormService<MetadataExtension>>() {}).to(new TypeLiteral<DormServiceImpl<MetadataExtension>>() {});
        bind(new TypeLiteral<DormService<DormMavenMetadata>>() {}).to(new TypeLiteral<DormServiceImpl<DormMavenMetadata>>() {});

        // maven service
        bind(MavenService.class).to(MavenServiceImpl.class);
    }
}
