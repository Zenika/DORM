package com.zenika.dorm.core.neo4j.domain.impl;

import com.zenika.dorm.core.neo4j.NodeRelationShip;
import com.zenika.dorm.core.neo4j.domain.DormNode;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DormNodeImpl implements DormNode{

    private static final String QUALIFIER_PROPERTY = "qualifier";
    private static final String VERSION_PROPERTY = "version";
    private final Node underlyingNode;

    public DormNodeImpl(final Node node){
        underlyingNode = node;
    }

    public Node getUnderlyingNode(){
        return underlyingNode;
    }

    public String getQualifier() {
        return (String) underlyingNode.getProperty(QUALIFIER_PROPERTY);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setQualifier(String qualifier) {
        underlyingNode.setProperty(QUALIFIER_PROPERTY, qualifier);
    }

    public String getVersion() {
        return (String) underlyingNode.getProperty(VERSION_PROPERTY);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setVersion(String version) {
        underlyingNode.setProperty(VERSION_PROPERTY, version);
    }

    public Iterable<DormNode> getNodes() {
        final List<DormNode> nodes = new LinkedList<DormNode>();
        for (Relationship rel : underlyingNode.getRelationships(NodeRelationShip.DEPEND, Direction.INCOMING)){
            nodes.add(new DormNodeImpl(rel.getEndNode()));
        }
        return null;
    }

    @Override
    public boolean equals(final Object otherActor){
        if (otherActor instanceof DormNode){
            return this.underlyingNode.equals(((DormNodeImpl)otherActor).getUnderlyingNode());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.underlyingNode.hashCode();
    }
}
