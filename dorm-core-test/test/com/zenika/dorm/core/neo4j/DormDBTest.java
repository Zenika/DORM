package com.zenika.dorm.core.neo4j;

import com.zenika.dorm.core.neo4j.domain.DormDependency;
import com.zenika.dorm.core.neo4j.domain.DormNode;
import com.zenika.dorm.core.neo4j.domain.impl.DormNodeImpl;
import com.zenika.dorm.core.neo4j.services.BasicService;
import com.zenika.dorm.core.neo4j.services.BasicServiceFactory;
import org.junit.*;
import org.neo4j.graphdb.*;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.EmbeddedReadOnlyGraphDatabase;

import static org.junit.Assert.*;

import java.util.List;


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
        DormNode node = basicService.createNode("node1", "1.2.0");
    }

    @Test
    public void createDependency(){
        DormNode node1 = basicService.createNode("node1", "1.2.0");
        DormNode node2 = basicService.createNode("node2", "1.3.1");
        DormNode node3 = basicService.createNode("node3", "1.0.0");
        DormNode node4 = basicService.createNode("node4", "1.0.0");
        DormNode node5 = basicService.createNode("node5", "1.0.0");
        DormNode node6 = basicService.createNode("node6", "1.0.0");
        DormNode node7 = basicService.createNode("node7", "1.0.0");
        DormNode node8 = basicService.createNode("node8", "1.0.0");
        DormDependency dependency = basicService.createDependency(node1, node2, "test", "depend");
        basicService.createDependency(node1, node3, "test", "depend");
        basicService.createDependency(node1, node4, "test", "depend");
        basicService.createDependency(node2, node5, "test", "depend");
        basicService.createDependency(node5, node6, "test", "depend");
        basicService.createDependency(node6, node2, "test", "null");
        basicService.createDependency(node7, node8, "test", "null");
        basicService.createDependency(node8, node1, "test", "depend");
        System.out.println("Dependency : " + dependency.getName());
    }

    @Test
    public void findNode(){
        DormNode node = basicService.getNode("node1");
//        System.out.println("Node qualifier : " + node.getQualifier());
//        System.out.println("Node version : " + node.getVersion());
    }

    @Test
    public void getNode(){
        DormNode node = basicService.getNode("node1");
        System.out.println("Test node : " + node);
        Iterable<DormNode> dormNodes = node.getNodes();
        for (DormNode dormNode : dormNodes){
            System.out.println("Test dependency : " + dormNode);
        }
    }

    @Test
    public void testTraverser(){
        DormNode node = basicService.getNode("node1");
        Traverser traverser = ((DormNodeImpl)node).getUnderlyingNode().traverse(Traverser.Order.DEPTH_FIRST, StopEvaluator.END_OF_GRAPH, ReturnableEvaluator.ALL_BUT_START_NODE, DynamicRelationshipType.withName("depend"), Direction.OUTGOING);
        System.out.println("Node dependency : ");
        for (Node node2 : traverser){
            DormNode currentNode = new DormNodeImpl(node2);
            System.out.println("At depth " + traverser.currentPosition().depth() + " => " + currentNode);
        }
    }

    @Test
    public void testDelete(){
        DormNode node33 = basicService.createNode("node33", "1.0.0");
        DormNode node34 = basicService.createNode("node34", "1.0.0");
        DormNode node35 = basicService.createNode("node35", "1.0.0");
        DormNode node36 = basicService.createNode("node36", "1.0.0");
        DormNode node37 = basicService.createNode("node37", "1.0.0");
        DormNode node38 = basicService.createNode("node38", "1.0.0");
        DormNode node39 = basicService.createNode("node39", "1.0.0");
        DormNode node40 = basicService.createNode("node40", "1.0.0");
        basicService.createDependency(node33, node34, "test", "depend");
        basicService.createDependency(node33, node35, "test", "depend");
        basicService.createDependency(node33, node36, "test", "depend");
        basicService.createDependency(node33, node37, "test", "depend");
        basicService.createDependency(node33, node38, "test", "depend");
        basicService.createDependency(node33, node39, "test", "depend");
        basicService.createDependency(node33, node40, "test", "depend");
        basicService.deleteNode(node33.getQualifier());
        DormNode node33Bis = basicService.getNode("node33");
        assertNull(node33Bis);
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
