package com.zenika.dorm.core.processor;

import com.google.inject.Singleton;
import com.zenika.dorm.core.model.DormMetadata;

import java.util.Map;

/**
 * Is this interface usefull ?
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public interface Processor {
    public Boolean push(DormMetadata metadata);

    public Boolean push(String origin, Map<String, String> properties);
}
