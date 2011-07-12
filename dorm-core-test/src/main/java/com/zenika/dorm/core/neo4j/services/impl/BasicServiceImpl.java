package com.zenika.dorm.core.neo4j.services.impl;

import com.zenika.dorm.core.neo4j.NodeRelationShip;
import com.zenika.dorm.core.neo4j.domain.DormDependency;
import com.zenika.dorm.core.neo4j.domain.DormNode;
import com.zenika.dorm.core.neo4j.domain.impl.DormDependencyImpl;
import com.zenika.dorm.core.neo4j.domain.impl.DormNodeImpl;
import com.zenika.dorm.core.neo4j.services.BasicService;
import com.zenika.dorm.core.neo4j.util.BasicSearchEngine;
import com.zenika.dorm.core.neo4j.util.BasicSearchEngineImpl;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.*;
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
    private BasicSearchEngine searchEngine;
    private PathFinder pathFinder;

    public BasicServiceImpl(GraphDatabaseService graph) {
        graphDbService = graph;
        indexService = new LuceneIndexService(graphDbService);
        searchEngine = new BasicSearchEngineImpl(graphDbService);
        Relationship rel = graph.getReferenceNode().getSingleRelationship(NodeRelationShip.DEFAULT,
                Direction.OUTGOING);

    }

    public DormNode createNode(String qualifier, String version) {
        Transaction tx = graphDbService.beginTx();
        final Node node;
        DormNode dormNode = null;
        try {
            Node alreadyExist = indexService.getSingleNode(NAME_INDEX, qualifier);
            if (alreadyExist == null) {
                node = graphDbService.createNode();
                dormNode = new DormNodeImpl(node);
                dormNode.setQualifier(qualifier);
                dormNode.setVersion(version);
//            searchEngine.indexNode(dormNode);
                indexService.index(node, NAME_INDEX, qualifier);
                tx.success();
            } else {
                System.out.println("Node with this qualifier already exists !");
                dormNode = new DormNodeImpl(alreadyExist);
                return dormNode;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.finish();
            return dormNode;  //To change body of implemented methods use File | Settings | File Templates.
        }

    }

    public DormDependency createDependency(DormNode parent, DormNode child, String name, String relationName) {
        Transaction tx = graphDbService.beginTx();
        final Node parentNode;
        final Node childNode;
        Relationship rel;
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
            boolean canCreate = true;
            if (!parentNode.getRelationships().iterator().hasNext()) {
                canCreate = true;
            } else {
                for (Relationship relationship : parentNode.getRelationships()) {//
                    if (relationship.getStartNode().equals(parentNode) && relationship.getEndNode().equals(childNode)) {
                        dependency = new DormDependencyImpl(relationship);
                        tx.finish();
                        return dependency;
                    }
                }
            }
            if (canCreate) {
                rel = parentNode.createRelationshipTo(childNode, DynamicRelationshipType.withName(relationName));
                dependency = new DormDependencyImpl(rel);
                if (name != null) {
                    dependency.setName(name);
                }
            }
            tx.success();
            return dependency;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.finish();
            return dependency;
        }

    }

    public void deleteNode(String qualifier) {
        Transaction tx = graphDbService.beginTx();
        try {
            Node node = indexService.getSingleNode(NAME_INDEX, qualifier);
            indexService.removeIndex(node, NAME_INDEX, qualifier);
            DormNode dormNode = new DormNodeImpl(node);
            for (DormNode currentNode : dormNode.getNodes()) {
                if (!dormNode.equals(currentNode)) {
                    for (Relationship rel : ((DormNodeImpl) dormNode).getUnderlyingNode().getRelationships()) {
                        rel.delete();
                    }
                }
            }
            node.delete();
            tx.success();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.finish();
        }
    }

    public DormNode getNode(String qualifier) {
        Node dormNode = indexService.getSingleNode(NAME_INDEX, qualifier);
//        Node dormNode = null;
//        if (dormNode == null) {
//            dormNode = searchEngine.searchNode(qualifier);
//        }
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
