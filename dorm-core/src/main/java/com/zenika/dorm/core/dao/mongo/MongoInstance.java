package com.zenika.dorm.core.dao.mongo;

import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public class MongoInstance {

    private Mongo instance;
    private String database;
    private DB db;
    private DBCollection artifacts;

    @Inject
    public MongoInstance(Mongo instance, @MongoDatabase String database, @ArtifactsCollection String artifacts) {
        this.instance = instance;
        this.database = database;

        this.db = instance.getDB(database);
        this.artifacts = db.getCollection(artifacts);
    }

    public Mongo getInstance() {
        return instance;
    }

    public String getDatabase() {
        return database;
    }

    public DBCollection getArtifacts() {
        return artifacts;
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @BindingAnnotation
    public @interface MongoDatabase {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @BindingAnnotation
    public @interface ArtifactsCollection {
    }
}