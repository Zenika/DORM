package com.zenika.dorm.core.processor.extension;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class ProcessorExtension {

    public abstract DormWebServiceResult pushFromGenericRequest(DormWebServiceRequest request);

    public abstract DormWebServiceResult push(DormWebServiceRequest request);

    public abstract DormWebServiceResult getFromGenericRequest(DormWebServiceRequest request);

    public abstract DormWebServiceResult get(DormWebServiceRequest request);

    public List<DormResource> getByLabel(String labelName) {
        throw new UnsupportedOperationException();
    }
}