package com.zenika.dorm.core.processor.extension;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface RequestAnalyser {

    public abstract boolean isKnownRequest(DormWebServiceRequest request);

    public abstract ProcessorExtension getExtension();
}
