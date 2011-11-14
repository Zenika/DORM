package com.zenika.dorm.core.model.ws;

import com.zenika.dorm.core.model.ws.builder.DormWebServiceAbstractBuilder;

import java.io.File;

/**
 * Default immutable dorm request
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DormWebServiceRequest extends DormWebServiceProcess {

    private final String userAgent;
    private final String usage;
    private final String filename;
    private final String repositoryName;
    private final File file;

    public DormWebServiceRequest(Builder builder) {

        super(builder);

        this.userAgent = builder.userAgent;
        this.usage = builder.usage;
        this.filename = builder.filename;
        this.file = builder.file;
        this.repositoryName = builder.repositoryName;
    }

    public String getUserAgent() {
        return userAgent;
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

    public String getRepositoryName() {
        return repositoryName;
    }

    public boolean hasFile() {
        return file != null;
    }

    public static class Builder extends DormWebServiceAbstractBuilder<Builder> {

        private String userAgent;
        private String usage;
        private String filename;
        private String repositoryName;
        private File file;

        public Builder() {

        }

        public Builder(DormWebServiceRequest request) {

            super(request);

            this.userAgent = request.getUserAgent();
            this.usage = request.getUsage();
            this.filename = request.getFilename();
            this.file = request.getFile();
            this.repositoryName = request.getRepositoryName();
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
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

        public Builder repositoryName(String repositoryName) {
            this.repositoryName = repositoryName;
            return this;
        }

        public DormWebServiceRequest build() {
            return new DormWebServiceRequest(this);
        }
    }
}
