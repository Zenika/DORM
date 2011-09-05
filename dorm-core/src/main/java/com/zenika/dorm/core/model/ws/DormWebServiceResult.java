package com.zenika.dorm.core.model.ws;

import com.zenika.dorm.core.model.ws.builder.DormWebServiceResultBuilder;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormWebServiceResult extends DormWebServiceProcess {

    private final boolean success;
    private final File file;

    public DormWebServiceResult(DormWebServiceResultBuilder builder) {
        super(builder);
        this.success = builder.getSuccess();
        this.file = builder.getFile();
    }

    public boolean isSuccess() {
        return success;
    }

    public File getFile() {
        return file;
    }
}
