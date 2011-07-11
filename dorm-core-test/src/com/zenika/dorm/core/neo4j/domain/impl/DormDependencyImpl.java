package com.zenika.dorm.core.neo4j.domain.impl;

import com.zenika.dorm.core.neo4j.domain.DormDependency;
import com.zenika.dorm.core.neo4j.domain.DormNode;
import org.neo4j.graphdb.Relationship;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class DormDependencyImpl implements DormDependency{

    private static final String NAME_PROPERTY = "name";

    private final Relationship underlyingRel;

    public DormDependencyImpl(final Relationship relationship){
        underlyingRel = relationship;
    }

    Relationship getUnderlyingRel(){
        return underlyingRel;
    }

    public String getName() {
        return (String) underlyingRel.getProperty(NAME_PROPERTY);
    }

    public void setName(String name) {
        underlyingRel.setProperty(NAME_PROPERTY, name);
    }

    public DormNode getParent() {
        return new DormNodeImpl(underlyingRel.getStartNode());
    }

    public DormNode getChild() {
        return new DormNodeImpl(underlyingRel.getEndNode());
    }
}
