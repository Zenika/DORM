package com.zenika.dorm.core.repository.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.repository.DormRepositoryResource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormRepositoryDeployEngine {

    private static final Logger LOG = LoggerFactory.getLogger(DormRepositoryDeployEngine.class);

    public boolean deploy(String location, File file) {

        File destination = new File(location);
        destination.mkdirs();

        try {
            FileUtils.moveFile(file, destination);
        } catch (IOException e) {
            throw new CoreException("Cannot move file to repository : " + destination, e);
        }
        return true;
    }
}
