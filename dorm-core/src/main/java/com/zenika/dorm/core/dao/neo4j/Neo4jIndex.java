package com.zenika.dorm.core.dao.neo4j;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@JsonAutoDetect
public class Neo4jIndex {

    private static final String NAME = "dependency";
    private static final Map<String, String> CONFIG;

    static {
        CONFIG = new HashMap<String, String>();
        CONFIG.put("provider", "lucene");
        CONFIG.put("type", "fulltext");
    }

    public static final String INDEX_PATH = "index/node";

    private String template;
    private String provider;
    private String type;

    public String getName() {
        return NAME;
    }

    public Map<String, String> getConfig() {
        return CONFIG;
    }

    @JsonIgnore
    public String getTemplate() {
        return template;
    }

    private void setTemplate(String template) {
        this.template = template;
    }

    @JsonIgnore
    public String getProvider() {
        return provider;
    }

    private void setProvider(String provider) {
        this.provider = provider;
    }

    @JsonIgnore
    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public String toString() {
        return "{\n" + "\t\"template\" : \"" + template + "\",\n\t\"provider\" : \"" + provider + "\",\n\t\"type\" : \"" + type + "\"\n}";
    }

  
}
