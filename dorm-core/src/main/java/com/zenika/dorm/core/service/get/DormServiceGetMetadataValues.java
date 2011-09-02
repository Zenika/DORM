package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormServiceGetMetadataValues {

    private DormMetadata metadata;
    private Usage usage;
    private Map<String, String> metadataExtensionClauses = new HashMap<String, String>();

    public DormServiceGetMetadataValues(DormMetadata metadata) {
        this.metadata = metadata;
    }

    public DormServiceGetMetadataValues withMetadataExtensionClause(String key, String value) {
        metadataExtensionClauses.put(key, value);
        return this;
    }

    public DormServiceGetMetadataValues usage(String usage) {
        this.usage = Usage.create(usage);
        return this;
    }

    public boolean isGetByQualifier() {
        return metadataExtensionClauses.isEmpty();
    }

    public boolean isGetByUsage() {
        return null != usage;
    }

    public DormMetadata getMetadata() {
        return metadata;
    }

    public Usage getUsage() {
        return usage;
    }

    public Map<String, String> getMetadataExtensionClauses() {
        return metadataExtensionClauses;
    }
}
