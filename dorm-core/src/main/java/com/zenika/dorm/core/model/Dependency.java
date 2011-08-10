package com.zenika.dorm.core.model;

import com.zenika.dorm.core.model.impl.Usage;

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