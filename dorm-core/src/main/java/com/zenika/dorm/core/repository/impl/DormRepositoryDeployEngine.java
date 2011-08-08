package com.zenika.dorm.core.repository.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.repository.DormRepository;
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

        LOG.debug("Deploy resource to the repository : " + resource);

        String path = FilenameUtils.normalizeNoEndSeparator(resource.getPath());
        LOG.trace("Deploy normalized path : " + path);

        File destination = new File(path);

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
