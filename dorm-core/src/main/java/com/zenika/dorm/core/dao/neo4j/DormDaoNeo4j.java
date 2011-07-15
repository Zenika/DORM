package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoNeo4j implements DormDao {

    @Override
    public Boolean push(DependencyNodeLeaf node) {
        return true;
    }

    @Override
    public Boolean push(DependencyNodeComposite node) {
        return true;
    }

    @Override
    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage) {
        return null;
    }

    @Override
    public Boolean push(DependencyNode node) {
        return null;
    }
}
