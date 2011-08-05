package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Immutable class that represents a dorm file
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDormFile implements DormFile {

    private final String name;
    private final String extension;
    private final File file;

    public static DormFile create(File file) {
        return new DefaultDormFile(file);
    }

    public static DefaultDormFile create(String filename, File file) {
        return new DefaultDormFile(FilenameUtils.getBaseName(filename), FilenameUtils.getExtension(filename),
                file);
    }

    public static DefaultDormFile create(String name, String extension, File file) {
        return new DefaultDormFile(name, extension, file);
    }

    /**
     * Temporary, do not use
     *
     * @param file
     */
    private DefaultDormFile(File file) {
        this.name = file.getName();
        this.file = file;
        this.extension = null;
    }

    private DefaultDormFile(String name, String extension, File file) {

        if (null == name || null == extension || null == file || name.isEmpty() || extension.isEmpty()) {
            throw new CoreException("Name, extension and file are required.");
        }

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExtension() {
        return extension;
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

    @Override
    public String toString() {
        return "DormFile { " +
                getFilename() + " }";
    }
}
