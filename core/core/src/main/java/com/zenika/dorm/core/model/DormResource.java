package com.zenika.dorm.core.model;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormResource {

    /**
     * The name without extension
     *
     * @return the name only
     */
    public String getName();

    /**
     * The extension without the dot
     *
     * @return the extension only
     */
    public String getExtension();

    /**
     * Get the file
     *
     * @return the file
     */
    public File getFile();
}