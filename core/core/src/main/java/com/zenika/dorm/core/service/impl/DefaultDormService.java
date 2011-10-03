package com.zenika.dorm.core.service.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataResult;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataValues;
import com.zenika.dorm.core.service.put.DormServicePutRequest;
import com.zenika.dorm.core.service.put.DormServiceStoreResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
    public void storeMetadata(DormMetadata metadata) {

        checkNotNull(metadata);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Store metadata : " + metadata);
        }

        dao.saveMetadata(metadata);
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
    public DormServiceGetMetadataResult getMetadata(DormServiceGetMetadataValues values) {

        checkNotNull(values);

        DormServiceGetMetadataResult result = new DormServiceGetMetadataResult();

        // if null then get by all usages
        Usage usage = values.getUsage();

        List<DormMetadata> metadatas = null;

        if (values.hasCompleteMetadata()) {

            DormBasicQuery query = new DormBasicQuery.Builder(values.getMetadata()).build();
            DormMetadata metadata = dao.get(query);

            if (null != metadata) {
                metadatas = new ArrayList<DormMetadata>();
                metadatas.add(metadata);
            }

        } else {
            metadatas = dao.getMetadataByExtension(values.getMetadata().getExtensionName(),
                    values.getMetadataExtensionClauses(), usage);
        }

        result.setMetadatas(metadatas);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Result from database : " + result);
        }

        return result;
    }

    @Override
    public DormResource getResource(DormMetadata metadata) {
        
        checkNotNull(metadata);

        return repository.get(metadata);
    }
}
