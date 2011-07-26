package com.zenika.dorm.core.dao.neo4j;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlRootElement
public class Neo4jMetadataResponse extends Neo4jResponse{

    private Neo4jMetadata data;

    public Neo4jMetadata getData() {
        return data;
    }

    public void setData(Neo4jMetadata data) {
        this.data = data;
    }
}
