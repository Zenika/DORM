package com.zenika.dorm.core.model.ws.builder;

import com.zenika.dorm.core.model.ws.DormWebServiceProcess;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormWebServiceResultBuilder extends DormWebServiceBuilder<DormWebServiceResultBuilder> {

    private File file;
    private boolean success;

    public DormWebServiceResultBuilder(String origin) {
        super(origin);
    }

    public DormWebServiceResultBuilder(DormWebServiceProcess request) {
        super(request);
    }

    @Override
    protected DormWebServiceResultBuilder self() {
        return this;
    }

    public DormWebServiceResultBuilder file(File file) {
        this.file = file;
        return this;
    }

    public DormWebServiceResultBuilder succeeded() {
        this.success = true;
        return this;
    }

    public DormWebServiceResultBuilder failed() {
        this.success = false;
        return this;
    }

    @Override
    public DormWebServiceResult build() {
        return new DormWebServiceResult(this);
    }

    public File getFile() {
        return file;
    }

    public boolean getSuccess() {
        return success;
    }
}
