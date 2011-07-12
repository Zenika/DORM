package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.model.DormMetadata;

import javax.inject.Singleton;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public interface Processor {
    public Boolean push(DormMetadata metadata);
}
