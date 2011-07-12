package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.processor.ProcessorExtension;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessor implements ProcessorExtension {

    @Override
    public Dependency push(DormMetadata metadata) {
        Dependency dependency = new DefaultDependency(metadata);
        return dependency;
    }
}
