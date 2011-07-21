package com.zenika.dorm.core.processor;

import com.google.inject.Singleton;
import com.zenika.dorm.core.model.DormRequest;

import java.io.File;
import java.util.Map;

/**
 * Is this interface usefull ?
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public interface Processor {

    public Boolean push(DormRequest request);

    public RequestProcessor getRequestProcessor();
}
