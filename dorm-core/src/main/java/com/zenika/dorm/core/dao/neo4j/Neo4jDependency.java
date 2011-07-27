package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormFile;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Neo4jDependency extends Neo4jNode implements Dependency{

    private Usage usage;
    private Neo4jMetadata metadata;

    public Neo4jDependency() {

    }

    public Neo4jDependency(Dependency dependency) {
        usage = dependency.getUsage();
        metadata = new Neo4jMetadata(dependency.getMetadata());
    }

    public URI getIndexURI(Neo4jIndex index) throws URISyntaxException {
        String template = index.getTemplate();
        template = template.replace("{key}", "fullqualifier");
        template = template.replace("{value}", metadata.getFullQualifier());
        return new URI(template);
    }

    @JsonIgnore
    public Usage getUsage() {
        return usage;
    }

    @JsonIgnore
    public void setUsage(Usage usage) {
        this.usage = usage;
    }

   @JsonIgnore
    public Neo4jMetadata getMetadata() {
        return metadata;
    }

    @JsonIgnore
    @Override
    public DormFile getFile() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @JsonIgnore
    @Override
    public Boolean hasFile() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @JsonIgnore
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

    @JsonIgnore
    @Override
    public void setProperties() {

    }
}
