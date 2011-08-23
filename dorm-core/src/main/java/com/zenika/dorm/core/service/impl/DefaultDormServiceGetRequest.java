package com.zenika.dorm.core.service.impl;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServiceGetRequest extends DefaultDormServiceProcess
        implements DormServiceGetRequest {

    private DormMetadataExtension metadataExtension;
    private Map<String, String> whereClause = new HashMap<String, String>();
    private boolean repositoryRequest;

    public DefaultDormServiceGetRequest(String processName, DormMetadataExtension metadataExtension) {
        super(processName);
        this.metadataExtension = metadataExtension;
    }

    @Override
    public void addWhereClause(String key, String value) {
        whereClause.put(key, value);
    }

    @Override
    public void addExtensionWhereClause(String key, String value) {
        whereClause.put(DormServiceGetRequest.EXTENSION_CLAUSE_PREFIX + key, value);
    }

    @Override
    public DormMetadataExtension getMetadataExtension() {
        return metadataExtension;
    }

    @Override
    public Map<String, String> getWhereClause() {
        return whereClause;
    }

    @Override
    public boolean isRepositoryRequest() {
        return repositoryRequest;
    }

    public void setWhereClause(Map<String, String> whereClause) {
        this.whereClause = whereClause;
    }

    public void setRepositoryRequest(boolean repositoryRequest) {
        this.repositoryRequest = repositoryRequest;
    }
}
