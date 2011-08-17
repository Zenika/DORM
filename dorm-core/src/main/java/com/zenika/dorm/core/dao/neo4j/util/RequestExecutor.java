package com.zenika.dorm.core.dao.neo4j.util;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.zenika.dorm.core.dao.neo4j.Neo4jIndex;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadataExtension;
import com.zenika.dorm.core.dao.neo4j.Neo4jNode;
import com.zenika.dorm.core.dao.neo4j.Neo4jRelationship;
import com.zenika.dorm.core.dao.neo4j.Neo4jResponse;
import com.zenika.dorm.core.dao.neo4j.Neo4jTraverse;
import com.zenika.dorm.core.model.DormMetadataExtension;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public interface RequestExecutor {
    <T extends Neo4jNode> void post(T node);

    void post(Neo4jRelationship relationship) throws URISyntaxException;

    Neo4jResponse<Map<String, String>> postExtension(Map<String, String> properties);

    Neo4jIndex post(Neo4jIndex index);

    void post(Neo4jNode node, URI indexUri);

    List<Neo4jRelationship> post(URI uri, Neo4jTraverse traverse);

    <T> T get(URI uri, Class<T> type);

    <T> T get(URI uri, Type type);

    <T extends Neo4jNode> T getNode(URI uri, Type type);

    public Neo4jMetadataExtension getExtension(URI uri, DormMetadataExtension dormExtension) throws ClientHandlerException, UniformInterfaceException;

    <T extends Neo4jNode> T getNode(URI uri);

    public String test();
}
