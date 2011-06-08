package com.zenika.dorm.core.dao;

import com.google.inject.ImplementedBy;
import com.zenika.dorm.core.dao.mongo.DormDaoMongo;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;

public interface DormDao {

    public <T extends MetadataExtension> DormArtifact<T> save(
            DormArtifact<T> artifact);

    public <T extends MetadataExtension> DormArtifact<T> getByMetadata(
            DormMetadata<T> metadata);

    public <T extends MetadataExtension> void removeByMetadata(
            DormMetadata<T> metadata);
}
