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

    public boolean deploy(DormRepositoryResource resource) {
        File destination = getDestination(resource);
        try {
            FileUtils.moveFile(resource.getFile(), destination);
        } catch (IOException e) {
            throw new CoreException("Cannot move file to repository : " + destination, e);
        }

        return true;
    }

    public void copy(DormRepositoryResource repositoryResource) {
        File destination = getDestination(repositoryResource);
        try {
            FileUtils.copyFile(repositoryResource.getFile(), destination);
        } catch (IOException e) {
            throw new CoreException("Cannot move file to repository : " + destination, e);
        }
    }

    private File getDestination(DormRepositoryResource repositoryResource) {
        String path = repositoryResource.getPath();
        File folders = new File(FilenameUtils.getPath(path));
        folders.mkdirs();
        File destination = new File(path);
        if (repositoryResource.isOverride() && destination.exists()) {
            LOG.trace("Override existing file at: {}", destination.getAbsolutePath());
            destination.delete();
        }
        LOG.debug("Deploy file to : {}", destination.getAbsolutePath());
        return destination;
    }
}
