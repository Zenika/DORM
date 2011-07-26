package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.graph.Dependency;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlRootElement
public class Neo4jDependencyResponse extends Neo4jResponse{

    private Neo4jDependency data;

    public Neo4jDependency getData() {
        return data;
    }

    public void setData(Neo4jDependency data) {
        this.data = data;
    }
}