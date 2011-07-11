package com.zenika.dorm.core.neo4j.domain;

import com.zenika.dorm.core.neo4j.domain.DormNode;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DormDependency {

    String getName();

    void setName(String name);

    DormNode getParent();

    DormNode getChild();

}
