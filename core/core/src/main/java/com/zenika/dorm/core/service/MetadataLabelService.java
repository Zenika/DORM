package com.zenika.dorm.core.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.repository.DormRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MetadataLabelService {

    @Inject
    private DormDao dao;

    @Inject
    private DormRepository repository;

    public <T extends DormMetadata> List<DormResource> getArtifactsByLabel(DormMetadataLabel<T> label) {

        label = dao.getByLabel(label);

        List<DormResource> resources = new ArrayList<DormResource>();

        for (T metadata : label.getMetadatas()) {
            DormResource resource = repository.get(metadata);
            resources.add(resource);
        }

        return resources;
    }
}
