package com.zenika.dorm.core.model.graph.proposal2;

import com.zenika.dorm.core.model.graph.proposal2.util.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/8/11
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class Node {

    private String qualifier;
    private String version;
    private String origin;
    private List<Node> nodes;
    private Scope scope;

    public Node(String qualifier) {
        this.qualifier = qualifier;
    }

    public Node(String qualifier, Scope scope) {
        this.qualifier = qualifier;
        this.scope = scope;
    }

    public void addNode(Node node){
        getNodes().add(node);
    }

    public List<Node> getNodes() {
        if (nodes == null){
            nodes = new ArrayList<Node>();
        }
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if ((this.qualifier == null) ? (other.qualifier != null) : !this.qualifier.equals(other.qualifier)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.qualifier != null ? this.qualifier.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Node{" + "qualifier=" + qualifier + ", version=" + version + ", origin=" + origin + "}'";
    }
}
