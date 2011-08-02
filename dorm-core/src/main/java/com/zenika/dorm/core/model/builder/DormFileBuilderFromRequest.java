package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormFile;

import java.io.File;

/**
 * Builder for dorm file with the respect of immutability
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormFileBuilderFromRequest {

    private String filename;
    private File file;

    public DormFileBuilderFromRequest(DormRequest request) {
        this.file = request.getFile();
        this.filename = request.getFilename();
    }

    public DormFile build() {
        return DefaultDormFile.create(filename, file);
    }
}
