package com.zenika.dorm.core.dao;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormDao {

    public Boolean push(DependencyNodeLeaf node);

    public Boolean push(DependencyNodeComposite node);
}
