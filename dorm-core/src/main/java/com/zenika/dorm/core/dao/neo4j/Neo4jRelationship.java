package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.graph.impl.Usage;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
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

    public Neo4jRelationship(){

    }

    public Neo4jRelationship(Usage usage){
        this.type = usage.getName();
    }

    public Neo4jRelationship(String from, String to, String type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public Neo4jRelationship(Neo4jNode from, Neo4jNode to, Usage usage){
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

    public void setType(String type){
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data){
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
}
