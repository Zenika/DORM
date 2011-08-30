package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.DormWebServiceRequest;
import com.zenika.dorm.core.model.impl.DefaultDormResource;

import java.io.File;

/**
 * Builder for dorm file with the respect of immutability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormResourceBuilderFromRequest {

    private String filename;
    private File file;

    public DormResourceBuilderFromRequest(DormWebServiceRequest request) {
        this.file = request.getFile();
        this.filename = request.getFilename();
    }

    public DormResource build() {
        return DefaultDormResource.create(filename, file);
    }
}
