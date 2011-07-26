package com.zenika.dorm.core.dao.neo4j;

import com.google.code.morphia.annotations.Transient;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Neo4jDependency extends Neo4jNode {

    private Neo4jUsage usage;
    private Neo4jMetadata metadata;

    public Neo4jDependency() {

    }

    public Neo4jDependency(Dependency dependency) {
        usage = new Neo4jUsage(dependency.getUsage());
        metadata = new Neo4jMetadata(dependency.getMetadata());
    }

    @XmlTransient
    public Neo4jUsage getUsage() {
        return usage;
    }

    @XmlTransient
    public void setUsage(Neo4jUsage usage) {
        this.usage = usage;
    }

    @XmlTransient
    public Neo4jMetadata getMetadata() {
        return metadata;
    }

    @XmlTransient
    public void setMetadata(Neo4jMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "Neo4jDependency{" +
                "usage=" + usage +
                ", metadata=" + metadata +
                '}';
    }
}
