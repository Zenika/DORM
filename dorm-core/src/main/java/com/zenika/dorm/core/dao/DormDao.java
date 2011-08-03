package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormDao {

    // todo: remove this when dao will be correct
    public void init();

    public Boolean push(Dependency node);

    public Boolean push(DependencyNode node);

    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage);
}
