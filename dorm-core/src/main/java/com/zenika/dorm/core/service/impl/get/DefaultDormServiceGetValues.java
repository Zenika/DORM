package com.zenika.dorm.core.service.impl.get;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.DormServiceValues;
import com.zenika.dorm.core.service.get.DormServiceGetValues;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServiceGetValues extends DormServiceValues implements DormServiceGetValues {

    private String version;
    private String qualifier;
    private Usage usage;
    private DormMetadataExtension metadataExtension;
    private Map<String, String> metadataExtensionClauses = new HashMap<String, String>();

    public DefaultDormServiceGetValues(DormMetadataExtension metadataExtension) {
        super(metadataExtension.getExtensionName());
        this.metadataExtension = metadataExtension;
    }

    @Override
    public void addMetadataExtensionClause(String key, String value) {
        metadataExtensionClauses.put(key, value);
    }

    @Override
    public boolean hasMetadataExtensionClauses() {
        return !metadataExtensionClauses.isEmpty();
    }

    @Override
    public boolean hasVersion() {
        return null != version;
    }

    @Override
    public boolean hasQualifier() {
        return null != qualifier;
    }

    @Override
    public Map<String, String> getMetadataExtensionClauses() {
        return metadataExtensionClauses;
    }

    @Override
    public DormMetadataExtension getMetadataExtension() {
        return metadataExtension;
    }

    @Override
    public Usage getUsage() {
        return usage;
    }

    @Override
    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getQualifier() {
        return qualifier;
    }

    @Override
    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("version", version)
                .append("qualifier", qualifier)
                .append("usage", usage)
                .append("metadataExtension", metadataExtension)
                .append("metadataExtensionClauses", metadataExtensionClauses)
                .appendSuper(super.toString())
                .toString();
    }
}
