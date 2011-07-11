package com.zenika.dorm.core.neo4j.util;

import com.zenika.dorm.core.neo4j.domain.DormNode;
import org.neo4j.graphdb.Node;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BasicSearchEngine {
    void indexNode(DormNode node);
    Node searchNode(String qualifier);
}
