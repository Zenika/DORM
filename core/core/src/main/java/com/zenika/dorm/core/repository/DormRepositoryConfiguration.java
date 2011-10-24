package com.zenika.dorm.core.repository;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormRepositoryConfiguration {

    private String name;
    private String basePath;
    private String pattern;
    private List<String> properties;

    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getBasePath() {
        return basePath;
    }

    @JsonProperty("base_path")
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getPattern() {
        return pattern;
    }

    @JsonProperty("pattern")
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<String> getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(List<String> properties) {
        this.properties = properties;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DormRepositoryConfiguration");
        sb.append("{basePath='").append(basePath).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", pattern='").append(pattern).append('\'');
        sb.append(", properties=").append(properties);
        sb.append('}');
        return sb.toString();
    }
}
