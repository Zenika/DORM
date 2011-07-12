package com.zenika.dorm.core.neo4j.services;

import com.zenika.dorm.core.neo4j.services.impl.BasicServiceImpl;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 6:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicServiceFactory {

    private static BasicService basicService;

    private BasicServiceFactory(){

    }

    public static BasicService getBasicService(GraphDatabaseService graphDatabaseService){
        if (basicService == null){
            return new BasicServiceImpl(graphDatabaseService);
        }
        return basicService;
    }



}
