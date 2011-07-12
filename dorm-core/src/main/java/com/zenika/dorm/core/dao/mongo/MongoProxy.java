package com.zenika.dorm.core.dao.mongo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import javax.inject.Inject;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MongoProxy  {

    private Datastore datastore;
    private Morphia morphia;


    @Inject
    public MongoProxy(Mongo mongo, Morphia morphia, String database) {
        this.morphia = morphia;
        this.datastore = morphia.createDatastore(mongo, database);
    }

//    public <T extends MetadataExtension> Query<DormArtifact<T>> createQuery() {
//
////        return datastore.createQuery(T.class);
//    }
}
