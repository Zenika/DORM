package com.zenika.dorm.core.neo4j;

import com.zenika.dorm.core.neo4j.domain.DormNode;
import com.zenika.dorm.core.neo4j.domain.impl.DormNodeImpl;
import com.zenika.dorm.core.neo4j.services.BasicService;
import com.zenika.dorm.core.neo4j.services.BasicServiceFactory;
import org.junit.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.EmbeddedReadOnlyGraphDatabase;


/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DormDBTest {

    private static GraphDatabaseService graphDatabaseService;
    private BasicService basicService;

    @BeforeClass
    public static void setUpClass(){
        graphDatabaseService = new EmbeddedGraphDatabase("/home/erouan/Software/neo4j-enterprise-1.4.M06/data/graph.db");
    }

    @AfterClass
    public static void tearDownClass(){
        graphDatabaseService.shutdown();
    }

    @Before
    public void setUp(){
        basicService = BasicServiceFactory.getBasicService(graphDatabaseService);
    }

    @After
    public void tearDown(){

    }

    @Test
    public void createNode(){
        basicService.createNode("node1");
    }


//    @Test
//    public void startDatabase() {
//        GraphDatabaseService graphDb = new EmbeddedGraphDatabase("/home/erouan/Software/neo4j-enterprise-1.4.M06/data/graph.db");
//
////        GraphDatabaseService graphDb =
//        Transaction tx = graphDb.beginTx();
//        try {
//            Node test = graphDb.createNode();
//            test.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            tx.finish();
//        }
//        graphDb.shutdown();
//    }
}
