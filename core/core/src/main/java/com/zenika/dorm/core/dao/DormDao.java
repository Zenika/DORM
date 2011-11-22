package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;

import java.util.List;

public interface DormDao {

    public DormMetadata get(DormBasicQuery query);

    public DormMetadataLabel getByLabel(DormMetadataLabel label);

    public void saveOrUpdateMetadata(DormMetadata metadata);

    public DependencyNode addDependenciesToNode(DependencyNode root);

    public DormMetadataLabel createOrUpdateLabel(DormMetadataLabel metadataLabel);

    public <T extends DormMetadata> T getById(long artifactId);
}
