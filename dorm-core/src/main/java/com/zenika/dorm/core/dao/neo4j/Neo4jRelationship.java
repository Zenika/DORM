package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.graph.impl.Usage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Neo4jRelationship {

    @XmlTransient
    private String from;
    private String to;
    private String type;
    private Map<String, Object> data;

    public Neo4jRelationship(){

    }

    public Neo4jRelationship(String from, String to, String type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public Neo4jRelationship(Neo4jNode from, Neo4jNode to, Usage usage){
        this.from = from.getResponse().getCreate_relationship();
        this.to = to.getResponse().getSelf();
        this.type = usage.getName();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
