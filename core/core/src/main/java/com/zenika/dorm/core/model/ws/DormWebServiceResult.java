package com.zenika.dorm.core.model.ws;

import com.zenika.dorm.core.model.ws.builder.DormWebServiceAbstractBuilder;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormWebServiceResult extends DormWebServiceProcess {

    public static enum Result {
        FOUND, NOTFOUND, ERROR
    }

    private final Result result;
    private final File file;

    public DormWebServiceResult(Builder builder) {
        super(builder);
        this.result = builder.result;
        this.file = builder.file;
    }

    public Result getResult() {
        return result;
    }

    public File getFile() {
        return file;
    }

    public static class Builder extends DormWebServiceAbstractBuilder<Builder> {

        private File file;
        private DormWebServiceResult.Result result;

        public Builder(String origin) {
            super(origin);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public Builder succeeded() {
            result = Result.FOUND;
            return this;
        }

        public Builder failed() {
            result = Result.ERROR;
            return this;
        }

        public Builder notfound() {
            result = Result.NOTFOUND;
            return this;
        }

        @Override
        public DormWebServiceResult build() {
            return new DormWebServiceResult(this);
        }
    }

}
