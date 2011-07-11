package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.model.graph.proposal1.Dependency;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyComposite extends Dependency {

    public DependencyComposite(DormMetadata dormMetadata, Usage usage) {
        super(dormMetadata, usage);
    }
}
