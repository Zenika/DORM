package com.zenika.dorm.maven.service;

import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.core.model.old.DormFile;
import com.zenika.dorm.core.modelnew.impl.DormModule;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;

import java.io.File;

public interface MavenService {

    public DormModule pushArtifact(MavenMetadataExtension origin, DormFile file);

    /**
     * @deprecated
     */
    public DormArtifact<DormMavenMetadata> pushArtifact(DormMavenMetadata mavenMetadata, File file, String filename);

    public DormArtifact<DormMavenMetadata> getArtifact(DormMavenMetadata mavenMetadata,
                                                       String filename);

    public void removeArtifact(DormMavenMetadata mavenMetadata, String filename);
}