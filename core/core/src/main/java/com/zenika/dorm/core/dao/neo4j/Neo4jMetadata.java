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

    private Map<String, String> properties;

    public Neo4jMetadata(){
        
    }

    public Neo4jMetadata(String extensionName, Map<String, String> properties) {
        this.extensionName = extensionName;
        this.properties = properties;
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

}
