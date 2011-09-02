package com.zenika.dorm.core.processor;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;

/**
 * Is this interface usefull ?
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface Processor {

    public DormWebServiceResult push(DormWebServiceRequest request);

    public DormWebServiceResult get(DormWebServiceRequest request);
}
