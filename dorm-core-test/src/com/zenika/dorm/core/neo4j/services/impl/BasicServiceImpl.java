package com.zenika.dorm.core.neo4j.services.impl;

import com.zenika.dorm.core.neo4j.NodeRelationShip;
import com.zenika.dorm.core.neo4j.domain.DormDependency;
import com.zenika.dorm.core.neo4j.domain.DormNode;
import com.zenika.dorm.core.neo4j.domain.impl.DormDependencyImpl;
import com.zenika.dorm.core.neo4j.domain.impl.DormNodeImpl;
import com.zenika.dorm.core.neo4j.services.BasicService;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.index.IndexService;
import org.neo4j.index.lucene.LuceneIndexService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicServiceImpl implements BasicService {

    private static final String TITLE_INDEX = "title";
    private static final String NAME_INDEX = "name";

    private GraphDatabaseService graphDbService;
    private IndexService indexService;
    private PathFinder pathFinder;

    public BasicServiceImpl(GraphDatabaseService graph) {
        graphDbService = graph;
        indexService = new LuceneIndexService(graphDbService);
    }

    public DormNode createNode(String qualifier) {
        Transaction tx = graphDbService.beginTx();
        final Node node;
        DormNode dormNode = null;
        try {
            node = graphDbService.createNode();
            dormNode = new DormNodeImpl(node);
            dormNode.setQualifier(qualifier);
            indexService.index(node, NAME_INDEX, qualifier);
            tx.success();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.finish();
            return dormNode;  //To change body of implemented methods use File | Settings | File Templates.
        }

    }

    public DormDependency createDependency(DormNode parent, DormNode child, String name) {
        Transaction tx = graphDbService.beginTx();
        final Node parentNode;
        final Node childNode;
        final Relationship rel;
        DormDependency dependency = null;
        try {
            if (parent == null) {
                throw new IllegalArgumentException("Null parent");
            }
            if (child == null) {
                throw new IllegalArgumentException("Null child");
            }
            parentNode = ((DormNodeImpl) parent).getUnderlyingNode();
            childNode = ((DormNodeImpl) child).getUnderlyingNode();
            rel = parentNode.createRelationshipTo(childNode, NodeRelationShip.DEPEND);
            dependency = new DormDependencyImpl(rel);
            if (name != null) {
                dependency.setName(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.finish();
            return dependency;
        }

    }

    public DormNode getNode(String qualifier) {
        Node dormNode = indexService.getSingleNode(NAME_INDEX, qualifier);
        if (dormNode == null) {
//                  TODO : dormNode = searchEngine.searchNode(qualifier);
        }
        DormNode node = null;
        if (dormNode != null) {
            node = new DormNodeImpl(dormNode);
        }
        return node;
    }

    public List<?> getBaconPath(DormNode node) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setupReferenceRelationship() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
