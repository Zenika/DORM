package com.zenika.dorm.core.dao.neo4j;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jLabel {

    private String label;

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
}
