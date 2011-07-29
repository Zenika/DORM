package com.zenika.dorm.core.dao.neo4j;


import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.graph.visitor.impl.DependenciesNodeCollector;
import com.zenika.dorm.core.model.DormMetadata;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoNeo4j implements DormDao {

    private Neo4jRequestExecutor executor;
    private Neo4jIndex index;

    public DormDaoNeo4j() {
        executor = new Neo4jRequestExecutor();
        index = new Neo4jIndex();
        index = executor.post(index);

    }

    @Override
    public Boolean push(Dependency dormDependency) {
        try {
            postDependency(dormDependency);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Neo4jDependency postDependency(Dependency dormDependency) {
        Usage usage = dormDependency.getUsage();
        Neo4jDependency dependency = null;
        try {
            dependency = new Neo4jDependency(dormDependency);
            Neo4jMetadata metadata = dependency.getMetadata();
            Neo4jMetadataExtension extension = dependency.getMetadata().getExtension();
            if (executor.get(dependency.getIndexURI(index), List.class).isEmpty()) {
                executor.post(extension);
                executor.post(metadata);
                executor.post(dependency);
                executor.post(new Neo4jRelationship(metadata, extension, Neo4jMetadataExtension.RELATIONSHIP_TYPE));
                executor.post(new Neo4jRelationship(dependency, metadata, Neo4jMetadata.RELATIONSHIP_TYPE));
                executor.post(dependency, dependency.getIndexURI(index));
            } else {
                dependency = searchNode(dependency.getIndexURI(index),
                        new TypeReference<List<Neo4jResponse<Neo4jDependency>>>() {
                        }.getType());
                fillNeo4jDependency(dependency);
                dependency.setUsage(usage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dependency;
    }

    private <T extends Neo4jNode> T searchNode(URI uri, Type type) throws URISyntaxException {
        List<Neo4jResponse<T>> responses = executor.get(uri, type);
        Neo4jResponse<T> response = responses.get(0);
        T node = response.getData();
        node.setResponse(response);
        node.setProperties();
        return node;
    }

    private Neo4jDependency fillNeo4jDependency(Neo4jDependency dependency) throws URISyntaxException {
        Neo4jRelationship dependencyMetadata = getSingleRelationship(dependency.getResponse()
                .getOutgoing_typed_relationships(Neo4jMetadata.RELATIONSHIP_TYPE));
        Neo4jMetadata metadata = executor.getNode(dependencyMetadata.getEnd(), new TypeReference<Neo4jResponse<Neo4jMetadata>>() {
        }.getType());
        Neo4jRelationship metadataExtension = getSingleRelationship(metadata.getResponse()
                .getOutgoing_typed_relationships(Neo4jMetadataExtension.RELATIONSHIP_TYPE));
        Neo4jMetadataExtension extension = executor.getNode(metadataExtension.getEnd(), new TypeReference<Neo4jResponse<Neo4jMetadataExtension>>() {
        }.getType());
        dependency.setMetadata(metadata);
        metadata.setExtension(extension);
        return dependency;
    }

    private Dependency getDependency(URI uri, Usage usage) throws URISyntaxException {
        Neo4jDependency dependency = executor.getNode(uri, new TypeReference<Neo4jResponse<Neo4jDependency>>() {
        }.getType());
        fillNeo4jDependency(dependency);
        dependency.setUsage(usage);
        return dependency;
    }

    private Neo4jRelationship getSingleRelationship(URI uri) {
        List<Neo4jRelationship> relationships = executor.get(uri, new TypeReference<List<Neo4jRelationship>>() {
        }.getType());
        return relationships.get(0);
    }

    @Override
    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage) {
        Map<String, DependencyNode> dependencyNodeMap = new HashMap<String, DependencyNode>();
        Neo4jDependency dependency = null;
        try {
            dependency = searchNode(Neo4jMetadata.generateIndexURI(metadata.getFullQualifier(), index),
                    new TypeReference<List<Neo4jResponse<Neo4jDependency>>>() {
                    }.getType());
            Neo4jTraverse traverse = new Neo4jTraverse(new Neo4jRelationship(usage));
            List<Neo4jRelationship> relationships = executor.post(dependency.getTraverse(Neo4jTraverse.RELATIONSHIP_TYPE), traverse);
            for (Neo4jRelationship relationship : relationships) {
                if (!(relationship.getType().equals(Neo4jMetadata.RELATIONSHIP_TYPE.getName()) || relationship.getType().equals(Neo4jMetadataExtension.RELATIONSHIP_TYPE.getName()))) {
                    DependencyNode dependencyParent = dependencyNodeMap.get(relationship.getStart());
                    DependencyNode dependencyChild = dependencyNodeMap.get(relationship.getEnd());
                    if (dependencyParent == null) {
                        dependencyParent = DefaultDependencyNode.create(getDependency(relationship.getStart(), usage));
                    }
                    if (dependencyChild == null) {
                        dependencyChild = DefaultDependencyNode.create(getDependency(relationship.getEnd(), usage));
                    }
                    dependencyParent.addChild(dependencyChild);
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return dependencyNodeMap.get(dependency.getResponse().getSelf());
    }

    @Override
    public Boolean push(DependencyNode node) {
        try {
            DependenciesNodeCollector visitor = new DependenciesNodeCollector(node.getDependency().getUsage());
            node.accept(visitor);
            Set<DependencyNode> nodes = visitor.getDependencies();
            for (DependencyNode currentNode : nodes) {
                Neo4jDependency dependency = postDependency(currentNode.getDependency());
                for (DependencyNode child : currentNode.getChildren()) {
                    Neo4jDependency dependencyChild = postDependency(child.getDependency());
                    executor.post(new Neo4jRelationship(dependency, dependencyChild, dependency.getUsage()));
                }
            }
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return false;
    }

}
