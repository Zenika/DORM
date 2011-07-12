package com.zenika.dorm.core.dao;

import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.core.model.old.DormMetadata;
import com.zenika.dorm.core.model.old.MetadataExtension;

/**
 * @param <T>
 * @deprecated
 */
public interface DormDaoOld<T extends MetadataExtension> {

    public DormArtifact<T> save(DormArtifact<T> artifact);

    public DormArtifact<T> getByMetadata(DormMetadata<T> metadata);

    public void removeByMetadata(DormMetadata<T> metadata);
}
