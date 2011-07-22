package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.DormMetadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlRootElement
public class Neo4jMetadata {

    private Neo4jOrigin origin;
    private String version;

    public Neo4jMetadata() {
    }

    public Neo4jMetadata(DormMetadata metadata) {
        this.version = metadata.getVersion();
        origin = new Neo4jOrigin(metadata.getExtension());
    }

    @XmlTransient
    public Neo4jOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(Neo4jOrigin origin) {
        this.origin = origin;
    }

    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
