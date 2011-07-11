package com.zenika.dorm.core.neo4j.domain;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DormNode {
    String getQualifier();

    void setQualifier(String qualifier);

    String getVersion();

    void setVersion(String version);

    Iterable<DormNode> getNodes();

//    void setNodes(List<DormNode> nodes);
}
