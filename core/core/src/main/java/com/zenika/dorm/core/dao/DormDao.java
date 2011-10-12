package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;

public interface DormDao {

    public DormMetadata get(DormBasicQuery query);

    public void saveOrUpdateMetadata(DormMetadata metadata);

    public DependencyNode addDependenciesToNode(DependencyNode root);
}
