package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DormServiceGetMetadataValues values = (DormServiceGetMetadataValues) o;

        if (metadata != null ? !metadata.equals(values.metadata) : values.metadata != null) return false;
        if (metadataExtensionClauses != null ? !metadataExtensionClauses.equals(values.metadataExtensionClauses) : values.metadataExtensionClauses != null)
            return false;
        if (usage != null ? !usage.equals(values.usage) : values.usage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = metadata != null ? metadata.hashCode() : 0;
        result = 31 * result + (usage != null ? usage.hashCode() : 0);
        result = 31 * result + (metadataExtensionClauses != null ? metadataExtensionClauses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("metadata", metadata)
                .append("usage", usage)
                .append("metadataExtensionClauses", metadataExtensionClauses)
                .appendSuper(super.toString())
                .toString();
    }
}
