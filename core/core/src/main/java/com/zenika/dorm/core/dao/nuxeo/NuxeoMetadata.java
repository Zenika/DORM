package com.zenika.dorm.core.dao.nuxeo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlRootElement
public class NuxeoMetadata {

    private String qualifier;
    private String extensionName;
    private String version;

    private Map<String, String> properties;

    public NuxeoMetadata() {

    }

    public NuxeoMetadata(String qualifier, String extensionName, String version, Map<String, String> properties) {
        this.qualifier = qualifier;
        this.extensionName = extensionName;
        this.version = version;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
