package com.zenika.dorm.core.model;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormResource {

    /**
     * The full name of the file : name + extension
     *
     * @return the full name
     */
    public String getFilename();

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

    public boolean exists();
}