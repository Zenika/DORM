package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.dao.neo4j.util.CustomSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jMetadata {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jMetadata.class);

    public static final String PROPERTIES_RELATIONSHIPS = "properties";

    private String extensionName;
    private String version;
    private String name;

    private Map<String, String> properties;

    public Neo4jMetadata() {

    }

    public Neo4jMetadata(String extensionName, String name, String version, Map<String, String> properties) {
        this.extensionName = extensionName;
        this.properties = properties;
        this.name = name;
        this.version = version;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Neo4jMetadata");
        sb.append("{extensionName='").append(extensionName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", properties=").append(properties);
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
