package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.Usage;

import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormServiceGetValues {

    public void addMetadataExtensionClause(String key, String value);

    public boolean hasMetadataExtensionClauses();

    public boolean hasVersion();

    public boolean hasQualifier();

    public Map<String, String> getMetadataExtensionClauses();

    public DormMetadataExtension getMetadataExtension();

    public Usage getUsage();

    public void setUsage(Usage usage);

    public String getVersion();

    public void setVersion(String version);

    public String getQualifier();

    public void setQualifier(String version);
}
