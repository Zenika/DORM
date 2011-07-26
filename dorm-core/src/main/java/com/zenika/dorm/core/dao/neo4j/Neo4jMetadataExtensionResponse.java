package com.zenika.dorm.core.dao.neo4j;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlRootElement
public class Neo4jMetadataExtensionResponse extends Neo4jResponse{

    private Neo4jMetadataExtension data;

    public Neo4jMetadataExtension getData() {
        return data;
    }

    public void setData(Neo4jMetadataExtension data) {
        this.data = data;
    }
}
