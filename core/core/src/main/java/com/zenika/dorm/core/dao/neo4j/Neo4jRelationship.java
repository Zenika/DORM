package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.impl.Usage;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */

public class Neo4jRelationship {

    private String from;
    private String to;
    private String type;

    public Neo4jRelationship(){

    }

    public Neo4jRelationship(String from, String to, String type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    @JsonIgnore
    public String getFrom() throws URISyntaxException {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

}
