package com.zenika.dorm.core.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DerivedObject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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

    @Override
    public boolean isDormMetadataAlreadyExist(DormMetadata dormMetadata) {
        DormBasicQuery basicQuery = new DormBasicQuery.Builder(dormMetadata).build();
        return dao.get(basicQuery) != null;
    }

    @Override
    public DormMetadata updateDormMetadata(DormMetadata dormMetadata) {
        return dao.saveOrUpdateMetadata(dormMetadata);
    }

    @Override
    public DormMetadata createDormMetadata(DormMetadata dormMetadata) {
        return dao.saveOrUpdateMetadata(dormMetadata);
    }

    @Override
    public void storeDerivedObject(DerivedObject derivedObject, File file) {
        repository.store(derivedObject, file);
    }
}