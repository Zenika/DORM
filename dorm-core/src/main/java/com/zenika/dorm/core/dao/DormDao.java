package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormDao {

    public Boolean push(Dependency node);

    public Boolean push(DependencyNodeComposite node);

    public Boolean push(DependencyNode node);

    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage);
}
