package com.zenika.dorm.core.model;

import java.io.File;
import java.io.InputStream;

public interface DormResource {

    /**
     * The name without extension
     */
    public String getName();

    /**
     * The extension without the dot
     */
    public String getExtension();

    /**
     * Get the file
     */
    public File getFile();

    public InputStream getInputStream();

    public boolean hasInputStream();

    public boolean hasFile();
}