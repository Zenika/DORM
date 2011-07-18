package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.model.DormProperties;

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
public class DefaultDormProperties implements DormProperties {

    private final static String VERSION = "version";
    private final static String USAGE = "usage";
    private final static String ORIGIN = "origin";
    private final static String FILENAME = "filename";

    private Set<String> reservedKeys = new HashSet<String>();
    private Map<String, String> properties = new HashMap<String, String>();
    private File file;

    private DefaultDormProperties() {
        reservedKeys.add(DefaultDormProperties.VERSION);
        reservedKeys.add(DefaultDormProperties.USAGE);
        reservedKeys.add(DefaultDormProperties.ORIGIN);
        reservedKeys.add(DefaultDormProperties.FILENAME);
    }

    public DefaultDormProperties(String version, String origin) {
        this();
        setVersion(version);
        setOrigin(origin);
    }

    @Override
    public void setOrigin(String origin) {
        properties.put(DefaultDormProperties.ORIGIN, origin);
    }

    @Override
    public String getOrigin() {
        return properties.get(DefaultDormProperties.ORIGIN);
    }

    @Override
    public void setFile(String filename, File file) {
        this.file = file;
        properties.put(DefaultDormProperties.FILENAME, filename);
    }

    @Override
    public String getFilename() {
        return properties.get(DefaultDormProperties.FILENAME);
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setVersion(String version) {
        properties.put(DefaultDormProperties.VERSION, version);
    }

    @Override
    public String getVersion() {
        return properties.get(DefaultDormProperties.VERSION);
    }

    @Override
    public void setUsage(String usage) {
        properties.put(DefaultDormProperties.USAGE, usage);
    }

    @Override
    public String getUsage() {
        return properties.get(DefaultDormProperties.USAGE);
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
