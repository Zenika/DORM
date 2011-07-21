package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormRequest;

import java.io.File;
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

    public static DefaultDormRequest create(Map<String, String> properties) {
        return new DefaultDormRequest(properties, null);
    }

    public static DefaultDormRequest create(Map<String, String> properties, File file) {
        return new DefaultDormRequest(properties, file);
    }

    /**
     * @param properties
     * @param file       optionnal, can be null
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
}
