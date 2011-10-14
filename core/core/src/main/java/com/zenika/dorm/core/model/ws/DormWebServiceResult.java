package com.zenika.dorm.core.model.ws;

import com.zenika.dorm.core.model.ws.builder.DormWebServiceAbstractBuilder;

import java.io.File;
import java.io.InputStream;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormWebServiceResult extends DormWebServiceProcess {

    public static enum Result {
        FOUND, NOTFOUND, ERROR
    }

    private final Result result;
    private final Object entity;

    public DormWebServiceResult(Builder builder) {
        super(builder);
        this.result = builder.result;
        if (builder.file == null){
            this.entity = builder.inputStream;
        } else {
            this.entity = builder.file;
        }
    }

    public Result getResult() {
        return result;
    }

    public Object getEntity(){
        return entity;
    }

    public static class Builder extends DormWebServiceAbstractBuilder<Builder> {

        private InputStream inputStream;
        private File file;
        private DormWebServiceResult.Result result;

        @Override
        protected Builder self() {
            return this;
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public Builder inputStream(InputStream inputStream){
            this.inputStream = inputStream;
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
