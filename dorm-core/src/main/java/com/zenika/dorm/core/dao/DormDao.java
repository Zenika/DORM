package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormDao {

    public Boolean push(Dependency node);

    public Boolean push(DependencyNode node);

    public Boolean pushWithParent(Dependency dependency, Dependency parent);

    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage);
}
