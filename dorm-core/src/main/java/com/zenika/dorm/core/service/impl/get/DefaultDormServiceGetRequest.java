package com.zenika.dorm.core.service.impl.get;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetValues;
import com.zenika.dorm.core.service.impl.DefaultDormServiceProcess;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServiceGetRequest extends DefaultDormServiceProcess
        implements DormServiceGetRequest {

    private boolean repositoryRequest;
    private boolean transitiveDependencies;
    private DormServiceGetValues values;

    public DefaultDormServiceGetRequest(String processName, DormMetadataExtension metadataExtension) {
        super(processName);
        values = new DefaultDormServiceGetValues(metadataExtension);
    }

    @Override
    public boolean isRepositoryRequest() {
        return repositoryRequest;
    }

    public void setRepositoryRequest(boolean repositoryRequest) {
        this.repositoryRequest = repositoryRequest;
    }

    @Override
    public boolean getTransitiveDependencies() {
        return transitiveDependencies;
    }

    public void setTransitiveDependencies(boolean transitiveDependencies) {
        this.transitiveDependencies = transitiveDependencies;
    }

    @Override
    public boolean isUniqueResultRequest() {
        return getValues().hasQualifier();
    }

    @Override
    public DormServiceGetValues getValues() {
        return values;
    }
}
