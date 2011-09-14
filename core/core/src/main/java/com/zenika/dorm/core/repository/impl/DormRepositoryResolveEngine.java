package com.zenika.dorm.core.repository.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        return null;
    }
}
