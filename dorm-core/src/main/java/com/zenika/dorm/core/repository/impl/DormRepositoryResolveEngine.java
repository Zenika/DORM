package com.zenika.dorm.core.repository.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryResource;
import org.apache.ivy.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormRepositoryResolveEngine {

    private static final Logger LOG = LoggerFactory.getLogger(DormRepositoryResolveEngine.class);

    private DormRepository repository;

    @Inject
    public DormRepositoryResolveEngine(DormRepository repository) {
        this.repository = repository;
    }

    public DormRepositoryResource resolve(String path) {
        LOG.debug("Resolve file from path : " + repository.getBase().getPath() + "/" + path);
        File file = FileUtil.resolveFile(repository.getBase(), path);
        // todo: has to check
//        if (file.exists()) {
            return new DefaultDormRepositoryResource(file, repository);
//        } else {
//            throw new RepositoryException("File doesn't exist in the repo").type(CoreException.Type.NULL);
//        }
    }
}
