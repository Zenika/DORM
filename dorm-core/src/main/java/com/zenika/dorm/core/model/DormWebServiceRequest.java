package com.zenika.dorm.core.model;

import java.io.File;
import java.util.Map;

/**
 * Wrapper for a dorm request with adding checking on required properties like version
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormWebServiceRequest {

    public String getOrigin();

    public String getUsage();

    public String getFilename();

    public File getFile();

    public boolean hasFile();

    public String getProperty(String key);

    public Map<String, String> getProperties();
}
