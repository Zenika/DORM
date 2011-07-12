package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;

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
}
