package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Neo4jMetadataExtension extends Neo4jNode {

    public static final Usage RELATIONSHIP_TYPE = Usage.create("EXTENSION");

    @JsonIgnore
    private Map<String, String> properties;

    private DormMetadata extension;

    public Neo4jMetadataExtension() {

    }

    public Neo4jMetadataExtension(DormMetadata extension) {
        this.extension = extension;
    }


    @Override
    public String toString() {
        return "Neo4jMetadataExtension{" +
                "extension='" + extension + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neo4jMetadataExtension)) return false;

        Neo4jMetadataExtension extension1 = (Neo4jMetadataExtension) o;

        if (extension != null ? !extension.equals(extension1.extension) : extension1.extension != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = extension != null ? extension.hashCode() : 0;
        return result;
    }

    @JsonIgnore
    @Override
    public void setProperties() {
//        this.setExtension(getResponse().getData().getNeo4jExtension());
//        this.setQualifier(getResponse().getData().getQualifier());
    }

    public DormMetadata getExtension() {
        return extension;
    }

    public void setExtension(DormMetadata extension) {
        this.extension = extension;
    }
}
