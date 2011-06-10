package com.zenika.dorm.core.service.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.helper.DormFileHelper;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;
import com.zenika.dorm.core.service.DormService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DormServiceImpl<T extends MetadataExtension> implements DormService<T> {

    @Inject
    private DormDao<T> dao;

    @Override
    public DormArtifact<T> pushArtifact(DormArtifact<T> artifact) {

        validateArtifact(artifact);

        try {
            DormArtifact<T> existing = getArtifact(artifact.getMetadata());

        } catch (RepositoryException e) {

            // artifact not found exception, it means we can push new artifact in the repo
            if (e.getType().equals(RepositoryException.Type.NULL)) {
//                return dao.save(artifact);
                return null;
            }

            throw e;
        }

        throw new ArtifactException("Artifact already exists").type(ArtifactException.Type.FORBIDDEN);
    }

    @Override
    public DormArtifact<T> pushArtifact(DormMetadata<T> metadata, DormFile file, List<DormArtifact<T>> dependencies) {
        DormArtifact<T> artifact = new DormArtifact<T>(metadata, file, dependencies);
        return pushArtifact(artifact);
    }

    @Override
    public DormArtifact<T> pushArtifact(DormMetadata<T> metadata, DormFile file) {
        return pushArtifact(new DormArtifact<T>(metadata, file));
    }

    @Override
    public DormArtifact<T> pushArtifact(DormMetadata<T> metadata, File file, String filename) {
        DormFile dormFile = DormFileHelper.createDormFileFromFilename(metadata, file, filename);
        return pushArtifact(metadata, dormFile);
    }

    @Override
    public DormArtifact<T> getArtifact(DormMetadata<T> metadata) {
        return dao.getByMetadata(metadata);
    }

    @Override
    public DormArtifact<T> updateArtifact(DormArtifact<T> artifact) {


        if (null == artifact.getFile() || null == artifact.getFile().getFile() || null == artifact.getFile().getName() || null == artifact.getFile().getExtension()) {
            throw new ArtifactException("New file is incomplete").type(ArtifactException.Type.NULL);
        }

        try {
            DormArtifact<T> existing = getArtifact(artifact.getMetadata());
        } catch (RepositoryException e) {
            if (e.getType().equals(RepositoryException.Type.NULL)) {
                throw new ArtifactException("Cannot update artifact metadata or artifact not found").type(ArtifactException.Type.FORBIDDEN);
            }

            throw e;
        }

//        return dao.save(artifact);
        return null;
    }

    @Override
    public DormArtifact<T> updateArtifact(DormMetadata<T> metadata, File file, String filename) {

        DormFile dormFile = DormFileHelper.createDormFileFromFilename(metadata, file, filename);
        DormArtifact<T> artifact = new DormArtifact<T>(metadata, dormFile);

        return updateArtifact(artifact);
    }

    @Override
    public void removeArtifact(DormMetadata<T> metadata) {
        dao.removeByMetadata(metadata);
    }

    @Override
    public void removeArtifact(DormArtifact<T> artifact) {
        removeArtifact(artifact.getMetadata());
    }

    private void validateArtifact(DormArtifact<T> artifact) {

        DormMetadata<T> metadata = artifact.getMetadata();

        // make some validation
        // TODO: externalize this process, with annotation system for example
        if (null == metadata || null == metadata.getName() || metadata.getName().trim().isEmpty() || null == metadata.getVersion()
                || metadata.getVersion().trim().isEmpty() || null == metadata.getOrigin()
                || metadata.getOrigin().trim().isEmpty()) {

            throw new ArtifactException("Missing dorm params").type(ArtifactException.Type.NULL);
        }

        if (null == artifact.getFile()) {
            artifact.setFile(new DormFile());
        }

        if (null == artifact.getDependencies()) {
            artifact.setDependencies(new ArrayList<DormArtifact<T>>());
        }
    }


}