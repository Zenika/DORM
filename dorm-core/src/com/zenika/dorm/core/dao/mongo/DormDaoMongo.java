package com.zenika.dorm.core.dao.mongo;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.sun.research.ws.wadl.Resource;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoMongo<T extends MetadataExtension> implements DormDao<T> {

    @Inject
    private MongoInstance instance;

    @Override
    public DormArtifact<T> save(DormArtifact<T> artifact) {

        BasicDBObject document = MongoMapping.getDocumentFromArtifact(artifact);

        instance.getArtifacts().save(document);

        return artifact;
    }

    @Override
    public DormArtifact<T> getByMetadata(DormMetadata<T> metadata) {

        BasicDBObject query = MongoMapping.getDocumentFromMetadata(metadata);

        DBObject res = instance.getArtifacts().findOne(query);

        if (null == res) {
            throw new RepositoryException("Artifact not found with metadata : " + metadata).type(RepositoryException.Type.NULL);
        }

        return MongoMapping.getArtifactFromDocument(res, metadata.getExtension().getClass());
    }

    @Override
    public void removeByMetadata(DormMetadata<T> metadata) {

        BasicDBObject query = MongoMapping.getDocumentFromMetadata(metadata);

        DBObject res = instance.getArtifacts().findOne(query);

        if(null == res) {
            throw new RepositoryException("Artifact not found with metadata : " + metadata).type(RepositoryException.Type.NULL);
        }

        instance.getArtifacts().remove(res);

//        Query query = ds.createQuery(DormMetadata.class);
//        query.field("name").equal(metadata.getName());
//        query.field("version").equal(metadata.getVersion());
//        query.field("origin").equal(metadata.getOrigin());
//
//        deleteByQuery(query);
    }
}
