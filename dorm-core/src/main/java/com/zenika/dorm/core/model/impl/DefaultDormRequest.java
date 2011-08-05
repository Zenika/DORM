package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Default immutable dorm request
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDormRequest implements DormRequest {

    private final String version;
    private final String origin;
    private final String usage;
    private final String filename;
    private final File file;

    /**
     * Additionnal properties
     */
    private final Map<String, String> properties = new HashMap<String, String>();

    /**
     * Build request from the builder
     *
     * @param builder
     */
    public DefaultDormRequest(DormRequestBuilder builder) {

        this.version = builder.getVersion();
        this.origin = builder.getOrigin();
        this.usage = builder.getUsage();

        if (null == version || null == origin) {
            throw new CoreException("Version and origin are required.");
        }

        if (null != builder.getFilename() && null != builder.getFile()) {
            this.filename = builder.getFilename();
            this.file = builder.getFile();
        } else {
            this.filename = null;
            this.file = null;
        }

        this.properties.putAll(builder.getProperties());
    }

    @Override
    public String getOrigin() {
        return origin;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public boolean hasFile() {
        return file != null && filename != null;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultDormRequest)) return false;

        DefaultDormRequest that = (DefaultDormRequest) o;

        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;
        if (origin != null ? !origin.equals(that.origin) : that.origin != null) return false;
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;
        if (usage != null ? !usage.equals(that.usage) : that.usage != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (usage != null ? usage.hashCode() : 0);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        String fileExists = (hasFile()) ? "yes" : "no";

        return "DormRequest { " +
                "version = " + version + "; " +
                "origin = " + origin + "; " +
                "usage = " + usage + "; " +
                "filename = " + filename + "; " +
                "java.io.file exists = " + fileExists + "; " +
                "additionnal properties = " + properties + " }";
    }
}
