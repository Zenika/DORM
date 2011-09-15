package com.zenika.dorm.core.dao.neo4j;

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


    public Neo4jTraverse() {
        this(null);
    }

    public Neo4jTraverse(Neo4jRelationship... relationshipsArray) {
        prune_evaluator = new HashMap<String, String>();
        prune_evaluator.put("name", "none");
        prune_evaluator.put("language", "builtin");
        relationships = new ArrayList<Map<String, String>>();
        for (Neo4jRelationship relationship : relationshipsArray) {
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

    @Override
    public String toString() {
        return "Neo4jTraverse{" +
                "order='" + order + '\'' +
                ", uniqueness='" + uniqueness + '\'' +
                ", prune_evaluator=" + prune_evaluator +
                ", relationships=" + relationships +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neo4jTraverse)) return false;

        Neo4jTraverse traverse = (Neo4jTraverse) o;

        if (order != null ? !order.equals(traverse.order) : traverse.order != null) return false;
        if (prune_evaluator != null ? !prune_evaluator.equals(traverse.prune_evaluator) : traverse.prune_evaluator != null)
            return false;
        if (relationships != null ? !relationships.equals(traverse.relationships) : traverse.relationships != null)
            return false;
        if (uniqueness != null ? !uniqueness.equals(traverse.uniqueness) : traverse.uniqueness != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (uniqueness != null ? uniqueness.hashCode() : 0);
        result = 31 * result + (prune_evaluator != null ? prune_evaluator.hashCode() : 0);
        result = 31 * result + (relationships != null ? relationships.hashCode() : 0);
        return result;
    }
}