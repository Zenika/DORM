package com.zenika.dorm.core.repository.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryResource;
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

        if (LOG.isDebugEnabled()) {
            LOG.debug("Resolve file at : " + path);
        }

        File file = new File(path);

        return new DormRepositoryResource(path, file);
    }
}
