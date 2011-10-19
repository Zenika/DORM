package com.zenika.dorm.core.processor.extension;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class ProcessorExtension {

    public abstract void push(String uri, File file);

    public abstract Object get(String request);
}
