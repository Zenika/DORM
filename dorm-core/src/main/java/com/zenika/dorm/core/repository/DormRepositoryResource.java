package com.zenika.dorm.core.repository;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormRepositoryResource {

    private String path;
    private File file;
    private DormRepository repository;

    public DormRepositoryResource(File file, DormRepository repository) {
        this.file = file;
        this.repository = repository;
    }

    public DormRepositoryResource(File file, DormRepository repository, String path) {
        this(file, repository);
        this.path = path;
    }

    public String getPath() {
        return repository.getBase() + "/" + path;
    }

    public File getFile() {
        return file;
    }

    public DormRepository getRepository() {
        return repository;
    }

    public boolean exists() {
        return (null != file);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("path", path)
                .append("file", file)
                .append("repository", repository)
                .appendSuper(super.toString())
                .toString();
    }
}