package com.zenika.dorm.core.service.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.service.DormService;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormService implements DormService {

    private boolean daoInitiated;

    // todo: remove this when dao will be correct
    private DormDao getDao() {
        if (!daoInitiated) {
            dao.init();
            daoInitiated = true;
        }

        return dao;
    }

    @Inject
    private DormDao dao;

    @Override
    public Boolean pushDependency(Dependency dependency) {
        return null;
    }

    @Override
    public Boolean pushNode(DependencyNode node) {
        return getDao().push(node);
    }

    @Override
    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage) {
        return getDao().getByMetadata(metadata, usage);
    }
}
