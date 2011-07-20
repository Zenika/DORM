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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultDormFile)) return false;

        DefaultDormFile that = (DefaultDormFile) o;

        if (extension != null ? !extension.equals(that.extension) : that.extension != null) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }
}
