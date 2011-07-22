package com.zenika.dorm.core.model;

import java.io.File;
import java.util.Map;

/**
 * Wrapper for a dorm request with adding checking on required properties like version
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormRequest {

    public final static String VERSION = "version";
    public final static String USAGE = "usage";
    public final static String ORIGIN = "origin";
    public final static String FILENAME = "filename";

    public String getOrigin();

    public String getFilename();

    public File getFile();

    public boolean hasFile();

    public String getVersion();

    public String getUsage();

    public String getProperty(String key);

    /**
     * The request properties in read only mode
     *
     * @return the request properties
     */
    public Map<String, String> getProperties();
}
