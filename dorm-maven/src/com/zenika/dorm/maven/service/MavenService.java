package com.zenika.dorm.maven.service;

import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;

import java.io.File;

public interface MavenService {

    public DormArtifact<DormMavenMetadata> pushArtifact(DormMavenMetadata mavenMetadata, File file, String filename);

    public DormArtifact<DormMavenMetadata> getArtifact(DormMavenMetadata mavenMetadata,
                                                       String filename);

    public void removeArtifact(DormMavenMetadata mavenMetadata, String filename);
}