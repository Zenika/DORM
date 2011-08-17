package com.zenika.dorm.core.repository.impl;

import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryResource;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormRepositoryResource implements DormRepositoryResource {

    private String path;
    private File file;
    private DormRepository repository;

    public DefaultDormRepositoryResource(File file, DormRepository repository) {
        this.file = file;
        this.repository = repository;
    }

    public DefaultDormRepositoryResource(File file, DormRepository repository, String path) {
        this(file, repository);
        this.path = path;
    }

    @Override
    public String getPath() {
        return repository.getBase() + "/" + path;
    }

    @Override
    public File getFile() {
        return file;
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
