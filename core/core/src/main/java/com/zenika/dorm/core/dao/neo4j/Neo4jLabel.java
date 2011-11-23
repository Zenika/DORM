package com.zenika.dorm.core.dao.neo4j;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jLabel {

    private String label;
    private Set<Neo4jResponse<Neo4jMetadata>> metadataResponseSet;

    public Neo4jLabel() {
    }

    public Neo4jLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @JsonIgnore
    public Set<Neo4jResponse<Neo4jMetadata>> getMetadataResponseSet() {
        return metadataResponseSet;
    }

    @JsonIgnore
    public void setMetadataResponseSet(Set<Neo4jResponse<Neo4jMetadata>> metadataResponseSet) {
        this.metadataResponseSet = metadataResponseSet;
    }
}
