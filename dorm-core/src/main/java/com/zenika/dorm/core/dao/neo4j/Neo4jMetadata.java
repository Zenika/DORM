package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Neo4jMetadata extends Neo4jNode implements DormMetadata {

    public static final Usage RELATIONSHIP_TYPE = Usage.create("METADATA");

    private Neo4jMetadataExtension extension;

    private String qualifier;
    private String version;
    private String fullQualifier;

    public Neo4jMetadata() {
    }

    public Neo4jMetadata(DormMetadata metadata) {
        qualifier = metadata.getQualifier();
        version = metadata.getVersion();
        fullQualifier = metadata.getFullQualifier();
        extension = new Neo4jMetadataExtension(metadata.getExtension());
    }

    @XmlElement
    @Override
    public String getQualifier() {
        return qualifier;
    }


    public String getVersion() {
        return version;
    }

    @JsonIgnore
    @Override
    public Neo4jMetadataExtension getExtension() {
        return extension;
    }


    @Override
    public String getFullQualifier() {
        return fullQualifier;
    }


    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    @JsonIgnore
    public void setExtension(Neo4jMetadataExtension extension) {
        this.extension = extension;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public void setFullQualifier(String fullQualifier) {
        this.fullQualifier = fullQualifier;
    }

    @Override
    public String toString() {
        return "Neo4jMetadata{" +
                "extension=" + extension +
                ", qualifier='" + qualifier + '\'' +
                ", version='" + version + '\'' +
                ", fullQualifier='" + fullQualifier + '\'' +
                '}';
    }

    @JsonIgnore
    @Override
    public void setProperties() {
//        this.setFullQualifier(getResponse().getData().getFullQualifier());
//        this.setQualifier(getResponse().getData().getQualifier());
//        this.setVersion(getResponse().getData().getVersion());
    }
}
