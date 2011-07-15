package com.zenika.dorm.core.model;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormFile {

    /**
     * Rename this as user specific
     * 
     * @return
     */
    public String getFilename();

    public File getFile();
}