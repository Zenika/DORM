package com.zenika.dorm.core.model;

import java.io.File;

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
}