package com.zenika.dorm.core.repository;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormRepositoryManager {

    public boolean put(Dependency dependency);

    public DormFile get(DormMetadata metadata);
}
