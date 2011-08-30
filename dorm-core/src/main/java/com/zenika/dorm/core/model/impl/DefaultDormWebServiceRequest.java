package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormWebServiceRequest;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Default immutable dorm request
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDormWebServiceRequest implements DormWebServiceRequest {

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
    public DefaultDormWebServiceRequest(DormRequestBuilder builder) {

        this.origin = builder.getOrigin();

        if (null == origin) {
            throw new CoreException("Webservice request origin is required.");
        }

        this.usage = builder.getUsage();
        this.filename = builder.getFilename();
        this.file = builder.getFile();
        this.properties.putAll(builder.getProperties());
    }

    @Override
    public String getOrigin() {
        return origin;
    }

    @Override
    public String getUsage() {
        return usage;
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
    public String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * The request properties in read only mode
     *
     * @return the request properties
     */
    @Override
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("origin", origin)
                .append("usage", usage)
                .append("filename", filename)
                .append("file", file)
                .append("properties", properties)
                .appendSuper(super.toString())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultDormWebServiceRequest that = (DefaultDormWebServiceRequest) o;

        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;
        if (origin != null ? !origin.equals(that.origin) : that.origin != null) return false;
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;
        if (usage != null ? !usage.equals(that.usage) : that.usage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = origin != null ? origin.hashCode() : 0;
        result = 31 * result + (usage != null ? usage.hashCode() : 0);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}
