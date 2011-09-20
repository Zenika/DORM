package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.apache.commons.logging.Log;
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

    private String qualifier;
    private String extensionName;

    private Map<String, String> properties;

    public Neo4jMetadata(String qualifier, String extensionName, Map<String, String> properties) {
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

    @JsonIgnore
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
