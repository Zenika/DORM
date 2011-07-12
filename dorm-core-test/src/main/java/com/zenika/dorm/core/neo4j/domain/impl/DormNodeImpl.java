package com.zenika.dorm.core.neo4j.domain.impl;

import com.zenika.dorm.core.neo4j.NodeRelationShip;
import com.zenika.dorm.core.neo4j.domain.DormNode;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;

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

    public void addDependency(DormNodeImpl otherNode, String scope){
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        try {
            if (!this.equals(otherNode)){
                Relationship rel = getDependencyRelationshipTo(otherNode, scope);
                if (rel == null){
                    underlyingNode.createRelationshipTo(otherNode.getUnderlyingNode(), DynamicRelationshipType.withName(scope));
                }
                tx.success();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            tx.finish();
        }
    }

    public int getNrOfDependencies(){
        return IteratorUtil.count(getDependencies());
    }

    public Iterable<DormNodeImpl> getDependencies(){
        return getDependenciesByDepth(1, NodeRelationShip.DEFAULT.toString());
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

    public Iterable<DormNode> getNodes(String relationName) {
        final List<DormNode> nodes = new LinkedList<DormNode>();
        for (Relationship rel : underlyingNode.getRelationships(DynamicRelationshipType.withName(relationName), Direction.OUTGOING)){
            nodes.add(new DormNodeImpl(rel.getEndNode()));
        }
        return nodes;
    }

    public Iterable<DormNode> getNodes() {
        final List<DormNode> nodes = new LinkedList<DormNode>();
        for (Relationship rel : underlyingNode.getRelationships(Direction.OUTGOING)){
            nodes.add(new DormNodeImpl(rel.getEndNode()));
        }
        return nodes;
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

    @Override
    public String toString(){
        return "Node : " + getQualifier() + ", version : " + getVersion();
    }

    private Iterable<DormNodeImpl> getDependenciesByDepth(int depth, String scope){
        TraversalDescription travDesc = Traversal.description()
                .breadthFirst()
                .relationships(DynamicRelationshipType.withName(scope))
                .uniqueness(Uniqueness.NODE_GLOBAL)
                .prune(Traversal.pruneAfterDepth(depth))
                .filter(Traversal.returnAllButStartNode());
        return createNodesFromPath(travDesc.traverse(underlyingNode));
    }

    private Relationship getDependencyRelationshipTo(DormNodeImpl otherDormNode){
        Node otherNode = otherDormNode.getUnderlyingNode();
        for (Relationship rel : underlyingNode.getRelationships()){
            if (rel.getOtherNode(underlyingNode).equals(otherNode)){
                return rel;
            }
        }
        return null;
    }
     private Relationship getDependencyRelationshipTo(DormNodeImpl otherDormNode, String scope){
        Node otherNode = otherDormNode.getUnderlyingNode();
        for (Relationship rel : underlyingNode.getRelationships(DynamicRelationshipType.withName(scope))){
            if (rel.getOtherNode(underlyingNode).equals(otherNode)){
                return rel;
            }
        }
        return null;
    }

    private IterableWrapper<DormNodeImpl, Path> createNodesFromPath(Traverser iterableToWrap){
        return new IterableWrapper<DormNodeImpl, Path>(iterableToWrap) {
            @Override
            protected DormNodeImpl underlyingObjectToObject(Path path) {
                return new DormNodeImpl(path.endNode());
            }
        };
    }
}
