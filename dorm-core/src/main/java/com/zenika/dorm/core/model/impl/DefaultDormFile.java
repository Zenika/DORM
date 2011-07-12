package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.model.DormFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDormFile implements DormFile {

    private String name;
    private String extension;
    private File file;

    public DefaultDormFile(String filename, File file) {
        this(FilenameUtils.getBaseName(filename), FilenameUtils.getExtension(filename), file);
    }

    public DefaultDormFile(String name, String extension, File file) {
        this.name = name;
        this.extension = extension;
        this.file = file;
    }

    @Override
    public String getFilename() {
        return name + "." + extension;
    }

    @Override
    public File getFile() {
        return file;
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

    public void setFile(File file) {
        this.file = file;
    }
}
