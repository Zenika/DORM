package com.zenika.dorm.core.neo4j.util;

import com.zenika.dorm.core.neo4j.domain.DormNode;
import com.zenika.dorm.core.neo4j.domain.impl.DormNodeImpl;
import com.zenika.dorm.core.neo4j.services.BasicServiceFactory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.index.IndexService;
import org.neo4j.index.lucene.LuceneIndexService;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicSearchEngineImpl implements BasicSearchEngine{
    private static final String NAME_PART_INDEX = "name.part";
    private static final String WORLD_PROPERTY = "world";
    private static final String COUNT_PROPERTY = "count_uses";

    private GraphDatabaseService graphDbService;

    private IndexService indexService;

    public BasicSearchEngineImpl(GraphDatabaseService graphDatabaseService){
        graphDbService = graphDatabaseService;
        indexService = new LuceneIndexService(graphDatabaseService);
    }

    public void indexNode(DormNode node) {
        index(node.getQualifier(), ((DormNodeImpl)node).getUnderlyingNode(), NAME_PART_INDEX, SearchRelType.PART_OF_NAME);
    }

    public Node searchNode(String qualifier) {
        return searchSingle(qualifier, NAME_PART_INDEX, SearchRelType.PART_OF_NAME);
    }

    private void index(final String value, final Node node, final String partIndexName, final SearchRelType relType){
        for (String part : splitSearchString(value)){
            Node wordNode = indexService.getSingleNode(partIndexName, part);
            if (wordNode == null){
                wordNode = graphDbService.createNode();
                indexService.index(wordNode, partIndexName, part);
                wordNode.setProperty(WORLD_PROPERTY, part);
            }
            wordNode.createRelationshipTo(node, relType);
            wordNode.setProperty(COUNT_PROPERTY, ((Integer)wordNode.getProperty(COUNT_PROPERTY, 0)) + 1);
        }
    }

    private Node searchSingle(final String value, final String indexName, final SearchRelType wordRelType){
        final List<Node> wordList = findSearchWorlds(value, indexName);
        if (wordList.isEmpty()){
            return null;
        }
        final Node startNode = wordList.remove(0);
        Node match = startNode.getRelationships(wordRelType).iterator().next().getEndNode();
        if (wordList.isEmpty()){
            return match;
        }
        int bestCount = 0;
        final int listSize = wordList.size();
        for (Relationship targetRel : startNode.getRelationships(wordRelType)){
            Node targetNode = targetRel.getEndNode();
            int hitCount = 0;
            for (Relationship wordRel : targetNode.getRelationships(wordRelType)){
                if (wordList.contains(wordRel.getStartNode())){
                    if (++hitCount == listSize){
                        return targetNode;
                    }
                }
            }
            if (hitCount > bestCount){
                match = targetNode;
                bestCount = hitCount;
            }
        }
        return match;
    }

    private List<Node> findSearchWorlds(final String userInput, final String partIndexName){
        final List<Node> wordList = new ArrayList<Node>();
        for (String part : splitSearchString(userInput)){
            Node wordNode = indexService.getSingleNode(partIndexName, part);
            if (wordNode == null || !wordNode.hasRelationship() || wordList.contains(wordNode)){
                continue;
            }
            wordList.add(wordNode);
        }
        if (wordList.isEmpty()){
            return Collections.emptyList();
        }
        Collections.sort(wordList, new Comparator<Node>() {
            public int compare(Node node, Node node1) {
                int leftCount = (Integer) node.getProperty(COUNT_PROPERTY, 0);
                int rightCount = (Integer) node1.getProperty(COUNT_PROPERTY, 0);
                return leftCount - rightCount;
            }
        });
        return  wordList;
    }
    
    private String[] splitSearchString(final String value){
        return value.toLowerCase(Locale.ENGLISH).split("[^\\w]+");
    }
}
