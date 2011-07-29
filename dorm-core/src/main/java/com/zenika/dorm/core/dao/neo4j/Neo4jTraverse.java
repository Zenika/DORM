package com.zenika.dorm.core.dao.neo4j;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jTraverse {

    public static final String NODE_TYPE = "node";
    public static final String RELATIONSHIP_TYPE = "relationship";
    public static final String PATH_TYPE = "path";
    public static final String FULL_PATH_TYPE = "fullpath";

    private final String order = "depth_first";
    private final String uniqueness = "node";
    private final Map<String, String> prune_evaluator;
    private final List<Map<String, String>> relationships;


    public Neo4jTraverse () {
        this(null);
    }

    public Neo4jTraverse(Neo4jRelationship ... relationshipsArray){
        prune_evaluator = new HashMap<String, String>();
        prune_evaluator.put("name", "none");
        prune_evaluator.put("language", "builtin");
        relationships = new ArrayList<Map<String, String>>();
        for (Neo4jRelationship relationship : relationshipsArray){
            Map<String, String> typedRelationship = new HashMap<String, String>();
            typedRelationship.put("direction", "out");
            typedRelationship.put("type", relationship.getType());
            relationships.add(typedRelationship);
        }
    }

    public String getOrder() {
        return order;
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public Map<String, String> getPrune_evaluator() {
        return prune_evaluator;
    }

    public List<Map<String, String>> getRelationships() {
        return relationships;
    }
}