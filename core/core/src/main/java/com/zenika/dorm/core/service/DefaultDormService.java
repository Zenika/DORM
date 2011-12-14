package com.zenika.dorm.core.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.security.DormSecurity;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormService implements DormService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDormService.class);

    @Inject
    private DormDao dao;

    @Inject
    private DormRepository repository;

    @Inject
    private DormSecurity security;

    @Override
    public void storeMetadata(DormMetadata metadata) {

        checkNotNull(metadata);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store metadata : " + metadata);
        }

        if (!security.getRole().canOverride() &&
                null != dao.get(new DormBasicQuery.Builder(metadata).build())) {
            throw new SecurityException("Cannot override existing metadata");
        }

        dao.saveOrUpdateMetadata(metadata);
    }

    @Override
    public void storeResource(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config) {

        checkNotNull(resource);
        checkNotNull(metadata);
        checkNotNull(config);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store resource : " + resource + " with config : " + config);
        }

        repository.store(resource, metadata, config);
    }

    @Override
    public DormResource getResource(DormMetadata metadata, String extension) {
        checkNotNull(metadata);
        return repository.get(metadata, extension);
    }

    @Override
    public DependencyNode addDependenciesToNode(DependencyNode node) {
        return dao.addDependenciesToNode(node);
    }
}