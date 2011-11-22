package com.zenika.dorm.core.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ImportDormService implements DormService {

    @Inject
    private DormDao dao;

    @Inject
    private DormRepository repository;

    @Override
    public void storeMetadata(DormMetadata metadata) {
        dao.saveOrUpdateMetadata(metadata);
    }

    @Override
    public void storeResource(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config) {
        dao.saveOrUpdateMetadata(metadata);
        repository.importResource(resource, metadata, config);
    }

    @Override
    public DormResource getResource(DormMetadata metadata) {
        return repository.get(metadata);
    }

    @Override
    public DependencyNode addDependenciesToNode(DependencyNode node) {
        throw new UnsupportedOperationException();
    }
}
