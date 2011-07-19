package com.zenika.dorm.core.dao.neo4j;


import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

import static com.zenika.dorm.core.dao.neo4j.util.Neo4jParser.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoNeo4j implements DormDao {

    private ObjectMapper mapper;
    private Neo4jRequestExecutor executor;

    public DormDaoNeo4j() {
        mapper = new ObjectMapper();
        mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        executor = new Neo4jRequestExecutor();
    }

    private String storeDependency(Dependency dependency) throws IOException {
        String originUri = null;
        String metadataUri = null;
        String dependencyUri = executor.getDependencyUri(dependency.getMetadata().getFullQualifier());
        if (dependencyUri == null) {
            try {
                originUri = executor.postNode(parseOriginProperty(dependency.getMetadata().getOrigin()));
                metadataUri = executor.postNode(parseMetaDataProperty(dependency.getMetadata()));
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
    public Boolean push(Dependency dependency) {
        try {
            String jsonRequest = parseDependencyToJson(dependency);
            executor.executeBatchRequests(jsonRequest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean pushNoBatch(Dependency dependency){
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
            String traverseJson = parseTraverse(usage);
            String dependencyUri = executor.getDependencyUri(metadata.getFullQualifier());
            return parseTraverseToDependency(metadata, usage, executor.getDependencyNode(dependencyUri, traverseJson))
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
            dependencyUri = getDependencyUriFromBatchResponse(executor.executeBatchRequests(
                    parseDependencyToJson(dependency)));
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
