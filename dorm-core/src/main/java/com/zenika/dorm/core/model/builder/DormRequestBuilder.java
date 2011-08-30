package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.model.DormWebServiceRequest;
import com.zenika.dorm.core.model.impl.DefaultDormWebServiceRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Builder for the dorm request with the respect of immutability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormRequestBuilder {

    private String origin;
    private String usage;
    private String filename;
    private File file;
    private Map<String, String> properties = new HashMap<String, String>();

    /**
     * Start builder with required metadatas : origin
     *
     * @param origin
     */
    public DormRequestBuilder(String origin) {
        this.origin = origin;
    }

    /**
     * Start builder by copying existing request
     *
     * @param request
     */
    public DormRequestBuilder(DormWebServiceRequest request) {
        this.origin = request.getOrigin();
        this.usage = request.getUsage();
        this.filename = request.getFilename();
        this.file = request.getFile();
        this.properties.putAll(request.getProperties());
    }

    public DormRequestBuilder origin(String origin) {
        this.origin = origin;
        return this;
    }

    public DormRequestBuilder usage(String usage) {
        this.usage = usage;
        return this;
    }

    public DormRequestBuilder filename(String filename) {
        this.filename = filename;
        return this;
    }

    public DormRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public DormRequestBuilder property(String name, String value) {
        properties.put(name, value);
        return this;
    }

    public DormWebServiceRequest build() {
        return new DefaultDormWebServiceRequest(this);
    }

    public String getOrigin() {
        return origin;
    }

    public String getUsage() {
        return usage;
    }

    public String getFilename() {
        return filename;
    }

    public File getFile() {
        return file;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
