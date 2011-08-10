package com.zenika.dorm.core.service.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.service.DormService;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormService implements DormService {

    @Inject
    private DormDao dao;

    @Inject
    private DormRepository repository;

    /**
     * Store metadata on the database and file on the filesystem
     *
     * @param node
     * @return
     */
    @Override
    public Boolean push(DependencyNode node) {
        return dao.push(node);
    }

    @Override
    public DependencyNode get(DependencyNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Dependency getDependency(DormMetadata metadata, Usage usage) {
        DormResource resource = repository.get(metadata);
        return DefaultDependency.create(metadata, resource);
    }
}
