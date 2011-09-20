package com.zenika.dorm.core.dao.neo4j.util;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jHelper {

    public static String dormQualifierToNeo4jQualifier(String dormQualifier){
        return dormQualifier.replace("/", "!");
    }

    public static String neo4jQualifierToDormQualifier(String neo4jQualifier){
        return neo4jQualifier.replace("!", "/");
    }

}
