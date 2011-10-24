package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DormMetadata;

public interface DormDao {

    public DormMetadata getDormMetadata(DormBasicQuery query);

    public DormMetadata saveOrUpdateMetadata(DormMetadata metadata);

    public DependencyNode addDependenciesToNode(DependencyNode root);
}
