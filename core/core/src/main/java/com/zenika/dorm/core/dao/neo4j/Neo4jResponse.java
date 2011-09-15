package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.impl.Usage;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Neo4jResponse<T> {

    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

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

    public URI getOutgoing_typed_relationships(Usage usage) throws URISyntaxException {
        return new URI(getOutgoing_typed_relationships().replace("{-list|&|types}", usage.getName()));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neo4jResponse)) return false;

        Neo4jResponse response = (Neo4jResponse) o;

        if (all_relationships != null ? !all_relationships.equals(response.all_relationships) : response.all_relationships != null)
            return false;
        if (all_type_relationships != null ? !all_type_relationships.equals(response.all_type_relationships) : response.all_type_relationships != null)
            return false;
        if (create_relationship != null ? !create_relationship.equals(response.create_relationship) : response.create_relationship != null)
            return false;
        if (data != null ? !data.equals(response.data) : response.data != null) return false;
        if (incoming_relationships != null ? !incoming_relationships.equals(response.incoming_relationships) : response.incoming_relationships != null)
            return false;
        if (incoming_typed_relationships != null ? !incoming_typed_relationships.equals(response.incoming_typed_relationships) : response.incoming_typed_relationships != null)
            return false;
        if (outgoing_relationships != null ? !outgoing_relationships.equals(response.outgoing_relationships) : response.outgoing_relationships != null)
            return false;
        if (outgoing_typed_relationships != null ? !outgoing_typed_relationships.equals(response.outgoing_typed_relationships) : response.outgoing_typed_relationships != null)
            return false;
        if (properties != null ? !properties.equals(response.properties) : response.properties != null)
            return false;
        if (property != null ? !property.equals(response.property) : response.property != null) return false;
        if (self != null ? !self.equals(response.self) : response.self != null) return false;
        if (traverse != null ? !traverse.equals(response.traverse) : response.traverse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (self != null ? self.hashCode() : 0);
        result = 31 * result + (create_relationship != null ? create_relationship.hashCode() : 0);
        result = 31 * result + (all_relationships != null ? all_relationships.hashCode() : 0);
        result = 31 * result + (all_type_relationships != null ? all_type_relationships.hashCode() : 0);
        result = 31 * result + (incoming_relationships != null ? incoming_relationships.hashCode() : 0);
        result = 31 * result + (incoming_typed_relationships != null ? incoming_typed_relationships.hashCode() : 0);
        result = 31 * result + (outgoing_relationships != null ? outgoing_relationships.hashCode() : 0);
        result = 31 * result + (outgoing_typed_relationships != null ? outgoing_typed_relationships.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        result = 31 * result + (property != null ? property.hashCode() : 0);
        result = 31 * result + (traverse != null ? traverse.hashCode() : 0);
        return result;
    }
}
