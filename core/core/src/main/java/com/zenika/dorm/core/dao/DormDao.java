package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;

import java.util.List;
import java.util.Map;

public interface DormDao {

    public DormMetadata get(DormBasicQuery query);

    public List<DormMetadata> getMetadataByExtension(String extensionName, Map<String, String> extensionClauses, Usage usage);

    public void saveOrUpdateMetadata(DormMetadata metadata);

    public DependencyNode addDependenciesToNode(DependencyNode root);
}
