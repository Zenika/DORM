package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.graph.impl.Usage;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlRootElement
public class Neo4jUsage {

    private Usage usage;

    public Neo4jUsage(Usage usage){
        this.usage = usage;
    }
}
