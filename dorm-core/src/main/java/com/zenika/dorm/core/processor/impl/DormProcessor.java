package com.zenika.dorm.core.processor.impl;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;
import com.zenika.dorm.core.processor.ProcessorExtension;

import java.util.Map;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessor implements ProcessorExtension {

    @Override
    public Dependency push(DormMetadata metadata) {
        Dependency dependency = new DefaultDependency(metadata);
        return dependency;
    }

    @Override
    public DormOrigin getOrigin(Map<String, String> properties) {
        String name = properties.get("name");
        return new DefaultDormOrigin(name);
    }

    @Override
    public Map<DormOrigin, Set<DormOrigin>> getOrigins(Map<String, String> properties) {
        return null;
    }
}
