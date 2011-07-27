package com.zenika.dorm.core.dao.neo4j;


import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.exception.AlreadyExistException;
import com.zenika.dorm.core.dao.neo4j.util.Neo4jParser;
import com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoNeo4j implements DormDao {

    private ObjectMapper mapper;
    private Neo4jRequestExecutor executor;
    private Neo4jParser parser;
    private Neo4jIndex index;

    public DormDaoNeo4j() {
        index = new Neo4jIndex();
        mapper = new ObjectMapper();
        mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        executor = new Neo4jRequestExecutor();
        index = executor.post(index);
        parser = new Neo4jParser();
    }

    public void getDependency(DormMetadata metadata, Usage usage) {

    }

    private String storeDependency(Dependency dependency) throws IOException {
        String originUri = null;
        String metadataUri = null;
        String dependencyUri = executor.getDependencyUri(dependency.getMetadata().getFullQualifier());
        if (dependencyUri == null) {
            try {
                originUri = executor.postNode(parser.parseOriginProperty(dependency.getMetadata().getExtension()));
                metadataUri = executor.postNode(parser.parseMetaDataProperty(dependency.getMetadata()));
                dependencyUri = executor.postNode("{}");
                createInternalRelationship(metadataUri, originUri, executor.ORIGIN_RELATIONSHIP);
                createInternalRelationship(dependencyUri, metadataUri, executor.METADATA_RELATIONSHIP);
                executor.attacheIndexToDependency(dependencyUri, dependency.getMetadata().getFullQualifier(),
                        executor.createIndexForDependency());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dependencyUri;
    }


    private void createInternalRelationship(String node, String child, String name) throws IOException {
        executor.createRelationship(node, child, new Usage(name));
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

    private Neo4jDependency postDependency(Dependency dormDependency){
        try {
            Neo4jDependency dependency = new Neo4jDependency(dormDependency);
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
                throw new AlreadyExistException("The dependency already exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean pushNoBatch(Dependency dependency) {
        try {
            storeDependency(dependency);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage) {
        try {
            String traverseJson = parser.parseTraverse(usage);
            String dependencyUri = executor.getDependencyUri(metadata.getFullQualifier());
            return parser.parseTraverseToDependency(metadata, usage, executor.getDependencyNode(dependencyUri, traverseJson))
                    .get(dependencyUri);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public Boolean push(DependencyNode node) {
//        DependenciesNodeCollector visitor = new DependenciesNodeCollector(node.getDependency().getUsage());
//        node.accept(visitor);
//        Set<DependencyNode> nodes = visitor.getDependencies();
//        for (DependencyNode currentNode : nodes){
//            Neo4jDependency =
//        }
        return null;
    }

    public Boolean pushWithParentNoBatch(Dependency dependency, Dependency parent) {
        String dependencyUri = null;
        try {
            dependencyUri = storeDependency(dependency);
            if (dependencyUri != null) {
                String parentUri = executor.getDependencyUri(parent.getMetadata().getFullQualifier());
                executor.createRelationship(parentUri, dependencyUri, dependency.getUsage());
                return true;
            } else {
                throw new Exception("The dependency doesn't exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean pushWithParent(Dependency dependency, Dependency parent) {
        String dependencyUri = null;
        try {
            dependencyUri = parser.getDependencyUriFromBatchResponse(executor.executeBatchRequests(
                    parser.parseDependencyToJson(dependency)));
            if (dependencyUri != null) {
                String parentUri = executor.getDependencyUri(parent.getMetadata().getFullQualifier());
                executor.createRelationship(parentUri, dependencyUri, dependency.getUsage());
                return true;
            } else {
                throw new Exception("The dependency doesn't exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
