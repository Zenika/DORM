package com.zenika.dorm.core.processor;

import com.google.inject.Singleton;
import com.zenika.dorm.core.model.DormRequest;

/**
 * Is this interface usefull ?
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface Processor {

    public Boolean push(DormRequest request);
}
