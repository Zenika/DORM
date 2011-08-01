package com.zenika.dorm.core.dao.neo4j.util;

import com.zenika.dorm.core.dao.neo4j.Neo4jIndex;
import com.zenika.dorm.core.dao.neo4j.Neo4jNode;
import com.zenika.dorm.core.dao.neo4j.Neo4jRelationship;
import com.zenika.dorm.core.dao.neo4j.Neo4jTraverse;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public interface RequestExecutor {
    <T extends Neo4jNode> void post(T node);

    void post(Neo4jRelationship relationship) throws URISyntaxException;

    Neo4jIndex post(Neo4jIndex index);

    void post(Neo4jNode node, URI indexUri);

    List<Neo4jRelationship> post(URI uri, Neo4jTraverse traverse);

    <T> T get(URI uri, Class<T> type);

    <T> T get(URI uri, Type type);

    <T extends Neo4jNode> T getNode(URI uri, Type type);

    <T extends Neo4jNode> T getNode(URI uri);

    public String test();
}
