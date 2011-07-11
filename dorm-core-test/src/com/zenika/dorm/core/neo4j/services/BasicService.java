package com.zenika.dorm.core.neo4j.services;

import com.zenika.dorm.core.neo4j.domain.DormDependency;
import com.zenika.dorm.core.neo4j.domain.DormNode;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BasicService {

    DormNode createNode(String qualifier);
    DormDependency createDependency(DormNode parent, DormNode child, String name);
    DormNode getNode(String qualifier);
    List<?> getBaconPath(DormNode node);
    void setupReferenceRelationship();
}
