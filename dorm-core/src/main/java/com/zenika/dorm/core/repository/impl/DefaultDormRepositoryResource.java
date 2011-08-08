package com.zenika.dorm.core.repository.impl;

import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryResource;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormRepositoryResource implements DormRepositoryResource {

    private DormFile file;
    private DormRepository repository;
    private String path;

    public DefaultDormRepositoryResource(DormFile file, DormRepository repository) {
        this.file = file;
        this.repository = repository;
    }

    public DefaultDormRepositoryResource(DormFile file, DormRepository repository, String path) {
        this(file, repository);
        this.path = path;
    }

    @Override
    public String getPath() {
        return repository.getBase() + "/" + path;
    }

    @Override
    public String getPathFromRepository() {
        return path;
    }

    @Override
    public String getFilename() {
        return file.getFilename();
    }

    @Override
    public File getFile() {
        return file.getFile();
    }

    @Override
    public DormRepository getRepository() {
        return repository;
    }

    @Override
    public boolean exists() {
        return (null != file);
    }

    @Override
    public String toString() {
        return "DefaultDormRepositoryResource { }";
    }
}
