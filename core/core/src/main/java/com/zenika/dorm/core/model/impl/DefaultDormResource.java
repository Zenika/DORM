package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.model.DormResource;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.InputStream;

public final class DefaultDormResource implements DormResource {

    private final String name;
    private final String extension;
    private final File file;
    private final InputStream inputStream;

    public static DormResource create(File file) {
        return new DefaultDormResource(null, null, file);
    }

    public static DefaultDormResource create(String filename, File file) {
        return new DefaultDormResource(FilenameUtils.getBaseName(filename), FilenameUtils.getExtension(filename), file);
    }

    public DefaultDormResource(String name, String extension, File file) {
        this.name = name;
        this.extension = extension;
        this.file = file;
        this.inputStream = null;
    }

    public DefaultDormResource(String name, String extension, InputStream inputStream) {
        this.name = name;
        this.extension = extension;
        this.file = null;
        this.inputStream = inputStream;
    }
    
    @Override
    public File getFile() {
        return file;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public boolean hasInputStream() {
        return inputStream != null;
    }

    @Override
    public boolean hasFile() {
        return file != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultDormResource)) return false;

        DefaultDormResource that = (DefaultDormResource) o;

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

    @Override
    public String toString() {
        return "DormFile { " +
                getName() + "." + getExtension() + " }";
    }
}
