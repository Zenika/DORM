package com.zenika.dorm.core.dao.neo4j;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jMetadata  {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jMetadata.class);

    public static final String PROPERTIES_RELATIONSHIPS = "properties";

    private String extensionName;
    private String version;
    private String name;

    private Map<String, String> properties;

    public Neo4jMetadata(){
        
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

    @JsonIgnore
    public Map<String, String> getProperties() {
        return properties;
    }

    @JsonIgnore
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
}
