package com.zenika.dorm.core.model.graph.proposal2;

import com.zenika.dorm.core.model.graph.proposal2.util.FilterScope;
import com.zenika.dorm.core.model.graph.proposal2.util.NodeGraph;
import com.zenika.dorm.core.model.graph.proposal2.util.Scope;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ParanoidGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/8/11
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class Launcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println("\nCase 1: There are cycles.");
//        test(true);
//
//        System.out.println("\nCase 2: There are no cycle.");
//        test(false);
//
//        System.out.println("\nAll done");
//        System.exit(0);
        Node testNode = new Node("test 1", Scope.USE_1);
        Node testNode2 = new Node("test 2", Scope.USE_3);
        Node testNode3 = new Node("test 3", Scope.USE_1);
        Node testNode4 = new Node("test 4", Scope.USE_2);
        testNode.addNode(testNode2);
        testNode2.addNode(testNode3);
        testNode3.addNode(testNode);
        testNode.addNode(testNode4);
        NodeGraph test = new NodeGraph(DefaultEdge.class);
        test.setRootNode(testNode, new FilterScope(Scope.USE_1, Scope.USE_2));
        CycleDetector<Node, DefaultEdge> cycleDetector = new CycleDetector<Node, DefaultEdge>(test);
//        TopologicalOrderIterator<Node, DefaultEdge> orderIterator = new TopologicalOrderIterator<Node, DefaultEdge>(test);
//        Node v;
//        while(orderIterator.hasNext()){
//            v = orderIterator.next();
//            System.out.println(v);
//        }
//        GmlExporter<Node, DefaultEdge> exporter = new GmlExporter<Node, DefaultEdge>
        System.out.println(cycleDetector.detectCycles());
    }

    public static void test(boolean createCycles) {
        CycleDetector<Node, DefaultEdge> cycleDetectors;
        ParanoidGraph<Node, DefaultEdge> pg;
        DefaultDirectedGraph<Node, DefaultEdge> g;

        Node node_a = new Node("Node a");
        Node node_b = new Node("Node b");
        Node node_c = new Node("Node c");
        Node node_d = new Node("Node d");
        Node node_e = new Node("Node e");


        g = new DefaultDirectedGraph<Node, DefaultEdge>(DefaultEdge.class);
        g.addVertex(node_a);
        g.addVertex(node_b);
        g.addVertex(node_c);
        g.addVertex(node_d);
        g.addVertex(node_e);

        g.addEdge(node_b, node_a);
        g.addEdge(node_c, node_b);
        if (createCycles) {
            g.addEdge(node_a, node_c);
        }
        g.addEdge(node_e, node_d);
        if (createCycles) {
            g.addEdge(node_d, node_e);
        }

        try {
            pg = new ParanoidGraph<Node, DefaultEdge>(g);
        } catch (IllegalArgumentException e) {

        }
        System.out.println(g.toString());
        // Are there cucles in the dependencies.
        cycleDetectors = new CycleDetector<Node, DefaultEdge>(g);
        // Cycle(s) detected
        if (cycleDetectors.detectCycles()) {
            Iterator<Node> iterators;
            Set<Node> cycleVerticles;
            Set<Node> subCycle;
            Node cycle;

            System.out.println("Cycles detected");

            // Get all verticles involted in cycles.
            cycleVerticles = cycleDetectors.findCycles();

            // Loop through verticles with this vertex.
            while (!cycleVerticles.isEmpty()) {
                System.out.println("Cycle:");

                // Ger a vertex involted in a cylce.
                iterators = cycleVerticles.iterator();
                cycle = iterators.next();

                // Get all verticles involted with this vertex.
                subCycle = cycleDetectors.findCyclesContainingVertex(cycle);
                for (Node sub : subCycle) {
                    System.out.println("   " + sub);
                    // Remove vertex so thas this cycle is not encountered again.
                    cycleVerticles.remove(cycle);
                }
            }
        } // No cycles. Just output properly ordered verticles.
        else {
            Node v;
            TopologicalOrderIterator<Node, DefaultEdge> orderIterator;
            orderIterator = new TopologicalOrderIterator<Node, DefaultEdge>(g);
            System.out.println("\nOrdering");
            while (orderIterator.hasNext()) {
                v = orderIterator.next();
                System.out.println(v);
            }
        }
    }
}
