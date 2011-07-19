package com.zenika.dorm.core.model.graph.proposal2.util;

import com.zenika.dorm.core.model.graph.proposal2.Node;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/8/11
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeGraph extends AbstractBaseGraph<Node, DefaultEdge> implements DirectedGraph<Node, DefaultEdge>{

    List<Node> nodes = new ArrayList<Node>();
    FilterScope filterScope;


    public NodeGraph(Class<? extends DefaultEdge> edgeClass) {
        this(new ClassBasedEdgeFactory<Node, DefaultEdge>(edgeClass));
    }

    public NodeGraph(EdgeFactory<Node, DefaultEdge> ef) {
        super(ef, false, true);
    }

    public void setRootNode(Node node, FilterScope filterScope) {
        this.filterScope = filterScope;
        setAllNode(node);
        System.out.println(nodes);
        addVertexNode();
    }

    private void addVertexNode() {
        for (Node node : nodes) {
            addVertex(node);
        }
        for (Node node : nodes) {
            if (!node.getNodes().isEmpty()) {
                for (Node childNode : node.getNodes()) {
                    if (containsVertex(childNode)) {
                        addEdge(node, childNode);
                    }
                }
            }
        }
    }

    private void setAllNode(Node node) {
        nodes.add(node);
        for (Node childNode : node.getNodes()) {
            if (!nodes.contains((Node) childNode) && filterScope.containtScope(childNode.getScope())) {
                setAllNode((Node) childNode);
            }
        }
    }
}
