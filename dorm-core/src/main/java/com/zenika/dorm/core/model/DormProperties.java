package com.zenika.dorm.core.model;

import java.io.File;

/**
 * Wrapper for properties with adding checking on required properties like version
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormProperties {

    public void setOrigin(String origin);

    public String getOrigin();

    public void setFile(String filename, File file);

    public String getFilename();

    public File getFile();

    public void setVersion(String version);

    public String getVersion();

    public void setUsage(String usage);

    public String getUsage();

    public void setProperty(String key, String value);

    public String getProperty(String key);
}
