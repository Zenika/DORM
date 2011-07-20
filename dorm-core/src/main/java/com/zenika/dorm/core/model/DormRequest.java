package com.zenika.dorm.core.model;

import java.io.File;

/**
 * Wrapper for a dorm request with adding checking on required properties like version
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormRequest {

    public void setOrigin(String origin);

    public String getOrigin();

    public void setFile(String filename, File file);

    public String getFilename();

    public File getFile();

    public Boolean hasFile();

    public void setVersion(String version);

    public String getVersion();

    public void setUsage(String usage);

    public String getUsage();

    public void setProperty(String key, String value);

    public String getProperty(String key);
}
