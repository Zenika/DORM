package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.model.DormRequest;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Default implementation
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormRequest implements DormRequest {

    private final static String VERSION = "version";
    private final static String USAGE = "usage";
    private final static String ORIGIN = "origin";
    private final static String FILENAME = "filename";

    private Set<String> reservedKeys = new HashSet<String>();
    private Map<String, String> properties = new HashMap<String, String>();
    private File file;

    private DefaultDormRequest() {
        reservedKeys.add(DefaultDormRequest.VERSION);
        reservedKeys.add(DefaultDormRequest.USAGE);
        reservedKeys.add(DefaultDormRequest.ORIGIN);
        reservedKeys.add(DefaultDormRequest.FILENAME);
    }

    public DefaultDormRequest(String version, String origin) {
        this();
        setVersion(version);
        setOrigin(origin);
    }

    @Override
    public void setOrigin(String origin) {
        properties.put(DefaultDormRequest.ORIGIN, origin);
    }

    @Override
    public String getOrigin() {
        return properties.get(DefaultDormRequest.ORIGIN);
    }

    @Override
    public void setFile(String filename, File file) {
        this.file = file;
        properties.put(DefaultDormRequest.FILENAME, filename);
    }

    @Override
    public String getFilename() {
        return properties.get(DefaultDormRequest.FILENAME);
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public Boolean hasFile() {
        return getFile() != null && getFilename() != null;
    }

    @Override
    public void setVersion(String version) {
        properties.put(DefaultDormRequest.VERSION, version);
    }

    @Override
    public String getVersion() {
        return properties.get(DefaultDormRequest.VERSION);
    }

    @Override
    public void setUsage(String usage) {
        properties.put(DefaultDormRequest.USAGE, usage);
    }

    @Override
    public String getUsage() {
        return properties.get(DefaultDormRequest.USAGE);
    }

    @Override
    public void setProperty(String key, String value) {

        if (reservedKeys.contains(key)) {
            throw new IllegalArgumentException(key + "is a reserved property, use defined setters");
        }

        properties.put(key, value);
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }
}
