package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;

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
public class Neo4jMetadata extends Neo4jNode implements DormMetadata{

    private DormMetadata metadata;
    private Neo4jMetadataExtension extension;

    public Neo4jMetadata() {
    }

    public Neo4jMetadata(DormMetadata metadata) {
        this.metadata = metadata;
        extension = new Neo4jMetadataExtension(metadata.getExtension());
    }

    @XmlElement
    @Override
    public String getQualifier() {
        return metadata.getQualifier();
    }

    @XmlElement
    public String getVersion() {
        return metadata.getVersion();
    }

    @XmlTransient
    @Override
    public DormMetadataExtension getExtension() {
        return metadata.getExtension();
    }

    @XmlElement
    @Override
    public String getFullQualifier() {
        return metadata.getFullQualifier();
    }
}
