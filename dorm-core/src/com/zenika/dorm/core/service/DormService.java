package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;

import java.io.File;
import java.util.List;

public interface DormService {

    public final static String ORIGIN = "Dorm";

    public <T extends MetadataExtension> DormArtifact<T> pushArtifact(
            DormMetadata<T> metadata, DormFile file,
            List<DormArtifact<T>> dependencies);

    public <T extends MetadataExtension> DormArtifact<T> pushArtifact(DormMetadata<T> metadata, DormFile file);

    public <T extends MetadataExtension> DormArtifact<T> pushArtifact(DormMetadata<T> metadata, File file, String filename);

    public <T extends MetadataExtension> DormArtifact<T> pushArtifact(DormArtifact<T> artifact);

    public <T extends MetadataExtension> DormArtifact<T> getArtifact(
            DormMetadata<T> metadata);

    public <T extends MetadataExtension> DormArtifact<T> updateArtifact(DormMetadata<T> metadata, File file, String filename);

    public <T extends MetadataExtension> DormArtifact<T> updateArtifact(DormArtifact<T> artifact);

    public <T extends MetadataExtension> void removeArtifact(
            DormMetadata<T> metadata);

    public <T extends MetadataExtension> void removeArtifact(
            DormArtifact<T> artifact);
}
