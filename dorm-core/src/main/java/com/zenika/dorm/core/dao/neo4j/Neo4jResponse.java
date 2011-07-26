package com.zenika.dorm.core.dao.neo4j;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class Neo4jResponse {

    private String self;
    private String create_relationship;
    private String all_relationships;
    private String all_type_relationships;
    private String incoming_relationships;
    private String incoming_typed_relationships;
    private String outgoing_relationships;
    private String outgoing_typed_relationships;
    private String properties;
    private String property;
    private String traverse;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getCreate_relationship() {
        return create_relationship;
    }

    public void setCreate_relationship(String create_relationship) {
        this.create_relationship = create_relationship;
    }

    public String getAll_relationships() {
        return all_relationships;
    }

    public void setAll_relationships(String all_relationships) {
        this.all_relationships = all_relationships;
    }

    public String getAll_type_relationships() {
        return all_type_relationships;
    }

    public void setAll_type_relationships(String all_type_relationships) {
        this.all_type_relationships = all_type_relationships;
    }

    public String getIncoming_relationships() {
        return incoming_relationships;
    }

    public void setIncoming_relationships(String incoming_relationships) {
        this.incoming_relationships = incoming_relationships;
    }

    public String getIncoming_typed_relationships() {
        return incoming_typed_relationships;
    }

    public void setIncoming_typed_relationships(String incoming_typed_relationships) {
        this.incoming_typed_relationships = incoming_typed_relationships;
    }

    public String getOutgoing_relationships() {
        return outgoing_relationships;
    }

    public void setOutgoing_relationships(String outgoing_relationships) {
        this.outgoing_relationships = outgoing_relationships;
    }

    public String getOutgoing_typed_relationships() {
        return outgoing_typed_relationships;
    }

    public void setOutgoing_typed_relationships(String outgoing_typed_relationships) {
        this.outgoing_typed_relationships = outgoing_typed_relationships;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getTraverse() {
        return traverse;
    }

    public void setTraverse(String traverse) {
        this.traverse = traverse;
    }

    @Override
    public String toString() {
        return "Neo4jResponse{" +
                "self='" + self + '\'' +
                ", create_relationship='" + create_relationship + '\'' +
                ", all_relationships='" + all_relationships + '\'' +
                ", all_type_relationships='" + all_type_relationships + '\'' +
                ", incoming_relationships='" + incoming_relationships + '\'' +
                ", incoming_typed_relationships='" + incoming_typed_relationships + '\'' +
                ", outgoing_relationships='" + outgoing_relationships + '\'' +
                ", outgoing_typed_relationships='" + outgoing_typed_relationships + '\'' +
                ", properties='" + properties + '\'' +
                ", property='" + property + '\'' +
                ", traverse='" + traverse + '\'' +
                '}';
    }
}
