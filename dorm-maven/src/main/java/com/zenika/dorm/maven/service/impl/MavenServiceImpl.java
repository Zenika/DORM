package com.zenika.dorm.maven.service.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.helper.DormFileHelper;
import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.core.model.old.DormFile;
import com.zenika.dorm.core.model.old.DormMetadata;
import com.zenika.dorm.core.modelnew.impl.DormModule;
import com.zenika.dorm.core.service.DormServiceOld;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.importer.core.MavenImporterService;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.service.MavenService;

import java.io.File;

public class MavenServiceImpl implements MavenService {

    @Inject
    private DormServiceOld<DormMavenMetadata> dormService;

    @Override
    public DormModule pushArtifact(MavenMetadataExtension origin, DormFile file) {

//        DormModule
        return null;
    }

    @Override
    public DormArtifact<DormMavenMetadata> pushArtifact(DormMavenMetadata mavenMetadata, File file, String filename) {

        DormArtifact<DormMavenMetadata> artifact = createDormArtifact(mavenMetadata, file, filename);
        return dormService.pushArtifact(artifact);
    }

    @Override
    public DormArtifact<DormMavenMetadata> getArtifact(DormMavenMetadata mavenMetadata, String filename) {

        String extension = DormFileHelper.getExtensionFromFilename(filename);
        mavenMetadata.setExtension(extension);

        // get dorm metadata with maven
//        DormMetadata<DormMavenMetadata> metadata = MavenHelper.createDormMetadata(mavenMetadata);

//		return DormEcrComponentHelper.getComponent().getDormService().getArtifact(metadata);
        return null;
    }

    @Override
    public void removeArtifact(DormMavenMetadata mavenMetadata, String filename) {

        String extension = DormFileHelper.getExtensionFromFilename(filename);
        mavenMetadata.setExtension(extension);

        // get dorm metadata with maven
//        DormMetadata<DormMavenMetadata> metadata = MavenHelper.createDormMetadata(mavenMetadata);

//		DormEcrComponentHelper.getComponent().getDormService().removeArtifact(metadata);
    }

    public DormArtifact<DormMavenMetadata> getDependenciesFromPom(DormMavenMetadata pomMetadata) {

        MavenImporterService importerService = new MavenImporterService();
        DormArtifact<DormMavenMetadata> artifact = importerService.getFullArtifact(pomMetadata);

        return artifact;
    }

    private void mapMavenFile(DormMavenMetadata metadata, DormFile file) {

    }

    private DormFile createDormFile(DormMetadata<DormMavenMetadata> metadata, File file, String filename) {

        String extension = DormFileHelper.getExtensionFromFilename(filename);

        // maven jar
        if (extension.equalsIgnoreCase("jar")) {
            // TODO: some jar logic
        }

        // maven pom
        else if (extension.equalsIgnoreCase("pom")) {


        } else {
            throw new MavenException("Incorrect file type").type(ArtifactException.Type.NULL);
        }

        metadata.getExtension().setExtension(extension);

        return new DormFile(metadata.getFullQualifier(), extension, file);
    }

    private DormMetadata<DormMavenMetadata> createDormMetadata(DormMavenMetadata mavenMetadata) {

        // define the mapping between dorm and maven metadatas
        String name = mavenMetadata.getGroupId() + ":" + mavenMetadata.getArtifactId() + ":"
                + mavenMetadata.getVersion() + ":" + mavenMetadata.getExtension();

        // create new dorm metadata from maven
        DormMetadata<DormMavenMetadata> metadata = new DormMetadata<DormMavenMetadata>(name, mavenMetadata.getVersion(),
                DormMavenMetadata.ORIGIN);

        // set the maven extension
        metadata.setExtension(mavenMetadata);

        return metadata;
    }

    private DormArtifact<DormMavenMetadata> createDormArtifact(DormMavenMetadata mavenMetadata, File file, String filename) {

        DormMetadata<DormMavenMetadata> metadata = createDormMetadata(mavenMetadata);
        DormFile dormFile = createDormFile(metadata, file, filename);

        return new DormArtifact<DormMavenMetadata>(metadata, dormFile);
    }

}
