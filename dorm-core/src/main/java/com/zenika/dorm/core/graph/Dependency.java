package com.zenika.dorm.core.graph;

import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;

/**
 * Represents a dependency on an artifact + an usage
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface Dependency {

    public Usage getUsage();

    public DormMetadata getMetadata();

    public DormFile getFile();

    public Boolean hasFile();
}