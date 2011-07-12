package com.zenika.dorm.core.service;

import com.zenika.dorm.core.model.graph.proposal1.Dependency;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormService {

    public Boolean pushDependency(Dependency dependency);

    public Boolean pushDependency(Dependency dependency, Dependency parent);
}
