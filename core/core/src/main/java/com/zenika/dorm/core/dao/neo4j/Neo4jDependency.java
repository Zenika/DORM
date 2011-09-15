package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.Usage;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
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
        return Neo4jMetadata.generateIndexURI(metadata.getIdentifier(), index);
    }

    @JsonIgnore
    public Usage getUsage() {
        return usage;
    }

    @JsonIgnore
    public void setUsage(Usage usage) {
        this.usage = usage;
    }

//   @JsonIgnore
//    public Neo4jMetadata getMetadata() {
//        return metadata;
//    }

    @Override
    public DormMetadata getMetadata() {
        return null;
    }

    @JsonIgnore
    @Override
    public DormResource getResource() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @JsonIgnore
    @Override
    public Boolean hasResource() {
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
    public URI getTraverse(String type) throws URISyntaxException {
        return new URI(getResponse().getTraverse().replace("{returnType}", type));
    }
    
    @JsonIgnore
    @Override
    public void setProperties() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neo4jDependency)) return false;

        Neo4jDependency that = (Neo4jDependency) o;

        if (metadata != null ? !metadata.equals(that.metadata) : that.metadata != null) return false;
        if (usage != null ? !usage.equals(that.usage) : that.usage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usage != null ? usage.hashCode() : 0;
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        return result;
    }
}
