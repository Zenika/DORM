package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.impl.Usage;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Neo4jRelationship {

    public enum Direction {
        IN, OUT, ALL
    }

    public static final String LABEL_TYPE = "label";

    private String from;
    private String to;
    private String type;

    private String start;
    private String end;
    private String self;

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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Neo4jRelationship");
        sb.append("{end='").append(end).append('\'');
        sb.append(", from='").append(from).append('\'');
        sb.append(", start='").append(start).append('\'');
        sb.append(", to='").append(to).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
