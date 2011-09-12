package com.zenika.dorm.core.repository;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormRepositoryResource {

    private String path;
    private File file;
    private boolean override;

    public DormRepositoryResource(String path, File file) {
        this.path = path;
        this.file = file;
    }

    public boolean exists() {
        return null != file && file.exists();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("path", path)
                .append("file", file)
                .appendSuper(super.toString())
                .toString();
    }
}