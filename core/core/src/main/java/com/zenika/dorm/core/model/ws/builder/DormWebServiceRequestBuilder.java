package com.zenika.dorm.core.model.ws.builder;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;

import java.io.File;

/**
 * Builder for the dorm request with the respect of immutability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormWebServiceRequestBuilder extends DormWebServiceBuilder<DormWebServiceRequestBuilder> {

    private String usage;
    private String filename;
    private File file;

    public DormWebServiceRequestBuilder(String origin) {
        super(origin);
    }

    public DormWebServiceRequestBuilder(DormWebServiceRequest request) {
        super(request);
        this.usage = request.getUsage();
        this.filename = request.getFilename();
        this.file = request.getFile();
    }

    @Override
    protected DormWebServiceRequestBuilder self() {
        return this;
    }

    public DormWebServiceRequestBuilder usage(String usage) {
        this.usage = usage;
        return this;
    }

    public DormWebServiceRequestBuilder filename(String filename) {
        this.filename = filename;
        return this;
    }

    public DormWebServiceRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public DormWebServiceRequest build() {
        return new DormWebServiceRequest(this);
    }

    public String getUsage() {
        return usage;
    }

    public String getFilename() {
        return filename;
    }

    public File getFile() {
        return file;
    }
}
