package com.zenika.dorm.core.dao.nuxeo;

import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoMetadata {

    private String qualifier;
    private String extensionName;

    private Map<String, String> properties;

    public NuxeoMetadata() {

    }

    public NuxeoMetadata(String qualifier, String extensionName, Map<String, String> properties) {
        this.qualifier = qualifier;
        this.extensionName = extensionName;
        this.properties = properties;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
