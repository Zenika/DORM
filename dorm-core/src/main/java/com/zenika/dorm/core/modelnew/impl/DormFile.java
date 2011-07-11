package com.zenika.dorm.core.modelnew.impl;

import java.io.File;

/**
 * Represents an artifact file stored on the file system
 */
public final class DormFile {

    private String name;
    private String extension;
    private File file;

    public DormFile() {

    }

    public DormFile(String name, String extension, File file) {
        this.name = name;
        this.extension = extension;
        this.file = file;
    }

    public String getFilename() {
        return name + "." + extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
