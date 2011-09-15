package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.impl.Usage;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Neo4jRelationship {

    private String from;
    private String to;
    private String type;
    private Map<String, Object> data;

    private String start;
    private String self;
    private String property;
    private String properties;
    private Map<String, Object> extensions;
    private String end;

    public Neo4jRelationship() {

    }

    public Neo4jRelationship(Usage usage) {
        this.type = usage.getName();
    }

    public Neo4jRelationship(String from, String to, String type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public Neo4jRelationship(Neo4jNode from, Neo4jNode to, Usage usage) {
        this.from = from.getResponse().getCreate_relationship();
        this.to = to.getResponse().getSelf();
        this.type = usage.getName();
    }

    @JsonIgnore
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @JsonIgnore
    public URI getStart() throws URISyntaxException {
        return new URI(start);
    }

    public void setStart(String start) {
        this.start = start;
    }

    @JsonIgnore
    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @JsonIgnore
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @JsonIgnore
    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @JsonIgnore
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    @JsonIgnore
    public URI getEnd() throws URISyntaxException {
        return new URI(end);
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Neo4jRelationship{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type='" + type + '\'' +
                ", data=" + data +
                ", start='" + start + '\'' +
                ", self='" + self + '\'' +
                ", property='" + property + '\'' +
                ", properties='" + properties + '\'' +
                ", extensions=" + extensions +
                ", end='" + end + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neo4jRelationship)) return false;

        Neo4jRelationship that = (Neo4jRelationship) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (extensions != null ? !extensions.equals(that.extensions) : that.extensions != null) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;
        if (property != null ? !property.equals(that.property) : that.property != null) return false;
        if (self != null ? !self.equals(that.self) : that.self != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (self != null ? self.hashCode() : 0);
        result = 31 * result + (property != null ? property.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        result = 31 * result + (extensions != null ? extensions.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }
}
