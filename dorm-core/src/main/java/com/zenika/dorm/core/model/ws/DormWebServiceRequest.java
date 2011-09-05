package com.zenika.dorm.core.model.ws;

import com.zenika.dorm.core.model.ws.builder.DormWebServiceRequestBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Default immutable dorm request
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DormWebServiceRequest extends DormWebServiceProcess {

    private final String usage;
    private final String filename;
    private final File file;

    public DormWebServiceRequest(DormWebServiceRequestBuilder builder) {

        super(builder);

        this.usage = builder.getUsage();
        this.filename = builder.getFilename();
        this.file = builder.getFile();
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

    public boolean hasFile() {
        return file != null && file.exists() && StringUtils.isNotBlank(filename);
    }
}
