package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.service.DormServiceProcess;

import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormServiceGetRequest extends DormServiceProcess {

    public static final String EXTENSION_CLAUSE_PREFIX = "extension.";
    public static final String CLAUSE_QUALIFIER = "qualifier";
    public static final String CLAUSE_VERSION = "version";
    public static final String CLAUSE_TYPE = "type";

    public void addWhereClause(String key, String value);

    public void addExtensionWhereClause(String key, String value);

    public DormMetadataExtension getMetadataExtension();

    public Map<String, String> getWhereClause();

    public boolean isRepositoryRequest();
}
