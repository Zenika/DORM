package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;

import java.util.Map;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ProcessorExtension {

    /**
     * @param metadata
     * @return
     * @deprecated already!
     */
    public Dependency push(DormMetadata metadata);

    /**
     * @param properties
     * @return
     */
    public DormOrigin getOrigin(Map<String, String> properties);

    public Map<DormOrigin, Set<DormOrigin>> getOrigins(Map<String, String> properties);
}
