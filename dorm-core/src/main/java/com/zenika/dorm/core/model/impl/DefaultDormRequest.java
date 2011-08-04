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

    /**
     * @param properties
     * @return
     * @deprecated use the dorm request builder
     */
    public static DefaultDormRequest create(Map<String, String> properties) {
        return new DefaultDormRequest(properties, null);
    }

    /**
     * @param properties
     * @param file
     * @return
     * @deprecated use the dorm request builder
     */
    public static DefaultDormRequest create(Map<String, String> properties, File file) {
        return new DefaultDormRequest(properties, file);
    }

    /**
     * @param request
     * @param newProperties
     * @return
     * @deprecated use the dorm request builder
     */
    public static DefaultDormRequest createFromRequest(DormRequest request, Map<String, String> newProperties) {
        return createFromRequest(request, newProperties, null);
    }

    /**
     * @param request
     * @param newFile
     * @return
     * @deprecated use the dorm request builder
     */
    public static DefaultDormRequest createFromRequest(DormRequest request, File newFile) {
        return createFromRequest(request, null, newFile);
    }

    /**
     * @param request
     * @param newProperties
     * @param newFile
     * @return
     * @deprecated use the dorm request builder
     */
    public static DefaultDormRequest createFromRequest(DormRequest request, Map<String,
            String> newProperties, File newFile) {

        Map<String, String> properties = new HashMap<String, String>(request.getProperties());

        // override old properties which are given by the new properties
        if (null != newProperties) {
            properties.putAll(newProperties);
        }

        // if no new file given, use the old one
        if (null == newFile) {
            newFile = request.getFile();
        }

        return new DefaultDormRequest(properties, newFile);
    }

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

    /**
     * @param properties
     * @param file       optionnal, can be null
     * @deprecated use the dorm request builder
     */
    private DefaultDormRequest(Map<String, String> properties, File file) {

        version = properties.get(DormRequest.VERSION);
        origin = properties.get(DormRequest.ORIGIN);
        usage = properties.get(DormRequest.USAGE);

        if (null == version || null == origin) {
            throw new CoreException("Version and origin are required.");
        }

        String filename = properties.get(DormRequest.FILENAME);

        if (null != filename && null != file) {
            this.filename = filename;
            this.file = file;
        } else {
            this.filename = null;
            this.file = null;
        }

        this.properties.putAll(properties);
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
