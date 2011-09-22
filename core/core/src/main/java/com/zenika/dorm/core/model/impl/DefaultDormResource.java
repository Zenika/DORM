package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormResource;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Immutable class that represents a dorm resource
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDormResource implements DormResource {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDormResource.class);

    private final String name;
    private final String extension;
    private final File file;

    public static DormResource create(File file) {
        return create(null, file);
    }

    public static DefaultDormResource create(String filename, File file) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Create dorm resource with filename : " + filename);
        }

        return create(FilenameUtils.getBaseName(filename), FilenameUtils.getExtension(filename), file);
    }

    public static DefaultDormResource create(String name, String extension, File file) {
        return new DefaultDormResource(name, extension, file);
    }

    private DefaultDormResource(String name, String extension, File file) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Create dorm resource with name : " + name + " and extension : " + extension);
        }

        if (null == file || !file.exists()) {
            throw new CoreException("File is required to create dorm resource");
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
                getFilename() + " }";
    }
}
