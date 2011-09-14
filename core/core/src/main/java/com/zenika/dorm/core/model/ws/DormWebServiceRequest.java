package com.zenika.dorm.core.model.ws;

import com.zenika.dorm.core.model.ws.builder.DormWebServiceAbstractBuilder;

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

    public DormWebServiceRequest(Builder builder) {

        super(builder);

        this.usage = builder.usage;
        this.filename = builder.filename;
        this.file = builder.file;
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
        return file != null;
    }

    public static class Builder extends DormWebServiceAbstractBuilder<Builder> {

        private String usage;
        private String filename;
        private File file;

        public Builder(String origin) {
            super(origin);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder usage(String usage) {
            this.usage = usage;
            return this;
        }

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public DormWebServiceRequest build() {
            return new DormWebServiceRequest(this);
        }
    }
}
