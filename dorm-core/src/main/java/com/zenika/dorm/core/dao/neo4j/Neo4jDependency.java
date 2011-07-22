package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlRootElement
public class Neo4jDependency {

    private Neo4jUsage usage;
    private Neo4jMetadata metadata;

    public Neo4jDependency(Dependency dependency){
        usage = new Neo4jUsage(dependency.getUsage());
        metadata = new Neo4jMetadata(dependency.getMetadata());
    }

}
