package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.core.model.old.DormFile;
import com.zenika.dorm.core.model.old.DormMetadata;
import com.zenika.dorm.core.model.old.MetadataExtension;

import java.io.File;
import java.util.List;

public interface DormService<T extends MetadataExtension> {

    public DormArtifact<T> pushArtifact(
            DormMetadata<T> metadata, DormFile file,
            List<DormArtifact<T>> dependencies);

    public DormArtifact<T> pushArtifact(DormArtifact<T> artifact);

    public DormArtifact<T> pushArtifact(DormMetadata<T> metadata, DormFile file);

    public DormArtifact<T> pushArtifact(DormMetadata<T> metadata, File file, String filename);

    public DormArtifact<T> getArtifact(
            DormMetadata<T> metadata);

    public DormArtifact<T> updateArtifact(DormMetadata<T> metadata, File file, String filename);

    public DormArtifact<T> updateArtifact(DormArtifact<T> artifact);

    public void removeArtifact(
            DormMetadata<T> metadata);

    public void removeArtifact(DormArtifact<T> artifact);
}
