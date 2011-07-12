package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ProcessorExtension {

    public Dependency push(DormMetadata metadata);
}
