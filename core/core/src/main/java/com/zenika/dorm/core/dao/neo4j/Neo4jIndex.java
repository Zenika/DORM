package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.exception.CoreException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@JsonAutoDetect
public class Neo4jIndex {

    public static final String LABEL_INDEX_NAME = "label";
    public static final String METADATA_INDEX_NAME = "metadata";
    public static final String INDEX_PATH = "index/node";

    private static final Map<String, String> CONFIG;

    static {
        CONFIG = new HashMap<String, String>();
        CONFIG.put("provider", "lucene");
        CONFIG.put("type", "fulltext");
    }

    private String template;
    private String provider;
    private String type;
    private String name;

    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name){
        this.name = name;
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

    @JsonIgnore
    public AssociatedNode addToIndex(String key, String value, String uri) {
        return new AssociatedNode(value, key, uri);
    }

    @JsonIgnore
    public URI fillIndexTemplatePath(String key, String value){
        String uri = template.replace("{key}", key).replace("{value}", value);
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new CoreException("Unable to create this uri: " + uri, e);
        }
    }

    @JsonIgnore
    public URI getIndexLocation() {
        String uri = template.substring(0, template.indexOf("/{key}/{value}"));
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new CoreException("Unable to create this uri: " + uri, e);
        }
    }

    private class AssociatedNode {

        private String value;
        private String key;
        private String nodeUri;

        private AssociatedNode() {
        }

        private AssociatedNode(String value, String key, String nodeUri) {
            this.value = value;
            this.key = key;
            this.nodeUri = nodeUri;
        }

        public String getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }

        @JsonProperty("uri")
        public String getNodeUri() {
            return nodeUri;
        }
    }
}
