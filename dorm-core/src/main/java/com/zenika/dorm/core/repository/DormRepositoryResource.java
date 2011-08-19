package com.zenika.dorm.core.repository;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormRepositoryResource {

    public String getPath();

    public File getFile();

    public DormRepository getRepository();

    public boolean exists();
}