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

    public static final String INDEX_PATH = "index/node";
    public static final String INDEX_DEFAULT_KEY = "qualifier";

    private static final String NAME = "metadata";
    private static final Map<String, String> CONFIG;

    static {
        CONFIG = new HashMap<String, String>();
        CONFIG.put("provider", "lucene");
        CONFIG.put("type", "fulltext");
    }

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

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setType(String type) {
        this.type = type;
    }
}
