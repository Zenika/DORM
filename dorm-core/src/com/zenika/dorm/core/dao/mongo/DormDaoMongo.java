package com.zenika.dorm.core.dao.mongo;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;

import java.net.UnknownHostException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoMongo implements DormDao {

    @Inject
    private MongoDB db;

    @Override
    public <T extends MetadataExtension> DormArtifact<T> save(DormArtifact<T> artifact) {

        BasicDBObject object = MongoMapping.getDocumentFromArtifact(artifact);

        db.getArtifactsCollection().save(object);

        return artifact;
    }

    @Override
    public <T extends MetadataExtension> DormArtifact<T> getByMetadata(DormMetadata<T> metadata) {

        BasicDBObject query = MongoMapping.getDocumentFromMetadata(metadata);

        DBObject res = db.getArtifactsCollection().findOne(query);

        DormArtifact<T> artifact = MongoMapping.getArtifactFromDocument(res);

        return artifact;
    }

    @Override
    public <T extends MetadataExtension> void removeByMetadata(DormMetadata<T> metadata) {

        BasicDBObject query = MongoMapping.getDocumentFromMetadata(metadata);

        db.getArtifactsCollection().remove(query);
    }
}
