package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormWebServiceRequest;

/**
 * Is this interface usefull ?
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface Processor {

    public Boolean push(DormWebServiceRequest request);

    public Dependency get(DormWebServiceRequest request);
}
