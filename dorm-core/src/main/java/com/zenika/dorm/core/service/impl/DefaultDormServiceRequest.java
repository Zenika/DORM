package com.zenika.dorm.core.service.impl;

import com.zenika.dorm.core.service.DormServiceRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DefaultDormServiceRequest extends DefaultDormServiceProcess
        implements DormServiceRequest {

    private boolean repositoryRequest;

    protected DefaultDormServiceRequest(String processName) {
        super(processName);
    }

    @Override
    public boolean isRepositoryRequest() {
        return repositoryRequest;
    }

    public void setRepositoryRequest(boolean repositoryRequest) {
        this.repositoryRequest = repositoryRequest;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("repositoryRequest", repositoryRequest)
                .appendSuper(super.toString())
                .toString();
    }
}