package com.zenika.dorm.core.service.impl;

import com.zenika.dorm.core.service.DormServiceRequest;

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
}
