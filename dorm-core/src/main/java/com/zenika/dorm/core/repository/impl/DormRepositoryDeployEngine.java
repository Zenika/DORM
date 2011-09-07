package com.zenika.dorm.core.repository.impl;

import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.repository.DormRepositoryResource;
import org.apache.commons.io.FilenameUtils;
import org.apache.ivy.util.CopyProgressEvent;
import org.apache.ivy.util.CopyProgressListener;
import org.apache.ivy.util.FileUtil;
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

        String path = resource.getPath();

        File folders = new File(FilenameUtils.getPath(path));
        folders.mkdirs();

        File destination = new File(path);

        if (resource.isOverride() && destination.exists()) {

            if (LOG.isTraceEnabled()) {
                LOG.trace("Override existing file at : " + destination.getAbsolutePath());
            }

            destination.delete();
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Deploy file to : " + destination.getAbsolutePath());
        }

        try {
            FileUtil.copy(resource.getFile().getAbsoluteFile(), destination.getAbsoluteFile(), new CopyProgressListener() {

                @Override
                public void start(CopyProgressEvent evt) {
                    LOG.trace("start " + evt);
                }

                @Override
                public void progress(CopyProgressEvent evt) {
                    LOG.trace("progress " + evt);
                }

                @Override
                public void end(CopyProgressEvent evt) {
                    LOG.trace("end " + evt);
                }
            });
        } catch (IOException e) {
            throw new RepositoryException("Cannot copy to the repository", e);
        }

        return true;
    }
}
