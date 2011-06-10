package com.zenika.dorm.core.dao.mongo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.mongodb.Mongo;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.MetadataExtension;
import org.bson.types.ObjectId;

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
