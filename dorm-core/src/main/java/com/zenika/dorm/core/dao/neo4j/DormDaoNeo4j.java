package com.zenika.dorm.core.dao.neo4j;


import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.exception.Neo4jDaoException;
import com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor;
import com.zenika.dorm.core.dao.neo4j.util.RequestExecutor;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.graph.visitor.impl.DependenciesNodeCollector;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoNeo4j implements DormDao {

    private static final Logger LOG = LoggerFactory.getLogger(DormDaoNeo4j.class);

    private static final TypeReference<List<Neo4jResponse<Neo4jDependency>>> TYPE_LIST_RESPONSE_DEPENDENCY = new TypeReference<List<Neo4jResponse<Neo4jDependency>>>() {
    };


    private RequestExecutor executor;
    private Neo4jIndex index;

    @Inject
    public DormDaoNeo4j() {
        this(new Neo4jRequestExecutor());
    }

    public DormDaoNeo4j(RequestExecutor executor) {
        this.executor = executor;
        index = new Neo4jIndex();
        try {
            index = this.executor.post(index);
        } catch (ClientHandlerException e) {
            LOG.error("The Neo4j dao can't connect with the Neo4j Database. Verify your configuration.", e);
            throw new Neo4jDaoException("The Neo4j dao can't connect with the Neo4j Database. Check your configuration.", e);
        }
    }

    public void init() {
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

    public Neo4jDependency postDependency(Dependency dormDependency) {
        Usage usage = dormDependency.getUsage();
        Neo4jDependency dependency = null;
        try {
            dependency = new Neo4jDependency(dormDependency);
            Neo4jMetadata metadata = dependency.getMetadata();
            Neo4jMetadataExtension extension = dependency.getMetadata().getNeo4jExtension();
            if (executor.get(dependency.getIndexURI(index), List.class).isEmpty()) {
                extension.setResponse(executor.postExtension(MetadataExtensionMapper.fromExtension(extension.getExtension())));
                executor.post(metadata);
                executor.post(dependency);
                executor.post(new Neo4jRelationship(metadata, extension, Neo4jMetadataExtension.RELATIONSHIP_TYPE));
                executor.post(new Neo4jRelationship(dependency, metadata, Neo4jMetadata.RELATIONSHIP_TYPE));
                executor.post(dependency, dependency.getIndexURI(index));
            } else {
                dependency = searchNode(dependency.getIndexURI(index),
                        new TypeReference<List<Neo4jResponse<Neo4jDependency>>>() {
                        }.getType());
                fillNeo4jDependency(dependency, dormDependency.getMetadata().getExtension());
                dependency.setUsage(usage);
            }
        } catch (URISyntaxException e) {
            throw new CoreException("URI syntax error", e);
        }
        return dependency;
    }

    public <T extends Neo4jNode> T searchNode(URI uri, Type type) {
        List<T> nodes = searchNodes(uri, type);
        if (nodes.size() > 1) {
            throw new Neo4jDaoException("Multiple Node found for this requests : " + uri + ". Maybe you should use the searchNodes method");
        }
        return nodes.get(0);
    }

    public <T extends Neo4jNode> List<T> searchNodes(URI uri, Type type) {
        List<Neo4jResponse<T>> responses = executor.get(uri, type);
        List<T> nodes = new ArrayList<T>();
        for (Neo4jResponse<T> response : responses) {
            T node = response.getData();
            node.setResponse(response);
            node.setProperties();
            nodes.add(node);
        }
        return nodes;
    }

    public Neo4jDependency fillNeo4jDependency(Neo4jDependency dependency, DormMetadataExtension extensionPlug) {
        try {
            Neo4jRelationship dependencyMetadata = getSingleRelationship(dependency.getResponse()
                    .getOutgoing_typed_relationships(Neo4jMetadata.RELATIONSHIP_TYPE));
            Neo4jMetadata metadata = executor.getNode(dependencyMetadata.getEnd(), new TypeReference<Neo4jResponse<Neo4jMetadata>>() {
            }.getType());
            Neo4jRelationship metadataExtension = getSingleRelationship(metadata.getResponse()
                    .getOutgoing_typed_relationships(Neo4jMetadataExtension.RELATIONSHIP_TYPE));
            Neo4jMetadataExtension extension = executor.getExtension(metadataExtension.getEnd(), extensionPlug);
            dependency.setMetadata(metadata);
            metadata.setExtension(extension);
            return dependency;
        } catch (URISyntaxException e) {
            throw new Neo4jDaoException("URI syntax exception", e);
        }
    }

    public Dependency getDependency(URI uri, Usage usage, DormMetadataExtension extension) throws URISyntaxException {
        Neo4jDependency dependency = executor.getNode(uri, new TypeReference<Neo4jResponse<Neo4jDependency>>() {
        }.getType());
        return getDependency(dependency, usage, extension);
    }

    public Dependency getDependency(Neo4jDependency dependency, Usage usage, DormMetadataExtension extension) {
        dependency = fillNeo4jDependency(dependency, extension);
        DormMetadata metadata = DefaultDormMetadata.create(dependency.getMetadata().getVersion(),
                dependency.getMetadata().getType(), dependency.getMetadata().getNeo4jExtension().getExtension());
        return DefaultDependency.create(metadata, usage);
    }

    private Neo4jRelationship getSingleRelationship(URI uri) {
        List<Neo4jRelationship> relationships = executor.get(uri, new TypeReference<List<Neo4jRelationship>>() {
        }.getType());
        return relationships.get(0);
    }

    @Override
    public DependencyNode getSingleByMetadata(DormMetadata metadata, Usage usage) {
        DormMetadataExtension extension = metadata.getExtension();
        Map<String, DependencyNode> dependencyNodeMap = new HashMap<String, DependencyNode>();
        Neo4jDependency dependency = null;
        try {
            TypeReference<List<Neo4jResponse<Neo4jDependency>>> type = new TypeReference<List<Neo4jResponse<Neo4jDependency>>>() {
            };
            dependency = searchNode(Neo4jMetadata.generateIndexURI(metadata.getQualifier(), index), type.getType());
            Neo4jTraverse traverse = new Neo4jTraverse(new Neo4jRelationship(usage));
            List<Neo4jRelationship> relationships = executor.post(dependency.getTraverse(Neo4jTraverse.RELATIONSHIP_TYPE), traverse);
            DependencyNode root = DefaultDependencyNode.create(getDependency(new URI(dependency.getUri()), usage, extension));
            dependencyNodeMap.put(dependency.getUri(), root);
            putChild(usage, dependencyNodeMap, relationships, extension);
        } catch (UniformInterfaceException e) {
            if (e.getResponse().getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                LOG.debug("The dependency node with this " + metadata.getQualifier() + " full qualifier doesn't " +
                        "found");
            }
        } catch (URISyntaxException e) {
            throw new Neo4jDaoException("URI syntax exception", e);
        }
        return dependencyNodeMap.get(dependency.getResponse().getSelf());
    }

    public DependencyNode getByMetadataExtension(DormMetadata metadata, Usage usage, Map<String, String> params) {
        List<Neo4jDependency> dependencies = executor.get(buildGremlinScript(params));
        DependencyNode node = DefaultDependencyNode.create(DefaultDependency.create(metadata, usage));
        for (Neo4jDependency dependency : dependencies){
            node.addChild(DefaultDependencyNode.create(getDependency(dependency, usage, metadata.getExtension())));
        }
        return node;
    }

    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage) {
        try {
            List<Neo4jDependency> dependencies = searchNodes(Neo4jMetadata.generateIndexURI(metadata.getQualifier(), index), TYPE_LIST_RESPONSE_DEPENDENCY.getType());
            DependencyNode node = DefaultDependencyNode.create(DefaultDependency.create(metadata, usage));
            for (Neo4jDependency dependency : dependencies) {
                node.getChildren().add(DefaultDependencyNode.create(getDependency(new URI(dependency.getUri()), usage, metadata.getExtension())));
            }
            return node;
        } catch (UniformInterfaceException e) {
            if (e.getResponse().getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                LOG.debug("No dependency node found with this " + metadata.getQualifier());
            }
            throw e;
        } catch (URISyntaxException e) {
            throw new Neo4jDaoException("URI syntax exception", e);
        }
    }

    public void putChild(Usage usage, Map<String, DependencyNode> dependencyNodeMap, List<Neo4jRelationship> relationships, DormMetadataExtension extension) throws URISyntaxException {

        for (Neo4jRelationship relationship : relationships) {
            DependencyNode dependencyParent = dependencyNodeMap.get(relationship.getStart());
            DependencyNode dependencyChild = dependencyNodeMap.get(relationship.getEnd());
            if (dependencyParent == null) {
                dependencyParent = DefaultDependencyNode.create(getDependency(relationship.getStart(), usage, extension));
                dependencyNodeMap.put(relationship.getStart().toString(), dependencyParent);
            }
            if (dependencyChild == null) {
                dependencyChild = DefaultDependencyNode.create(getDependency(relationship.getEnd(), usage, extension));
                dependencyNodeMap.put(relationship.getEnd().toString(), dependencyChild);
            }
            dependencyParent.addChild(dependencyChild);
        }
    }

    @Override
    public Boolean push(DependencyNode node) {
        // todo: fix this when dao is correct
        if (node.getChildren().isEmpty()) {
            return push(node.getDependency());
        }

        try {
            DependenciesNodeCollector visitor = new DependenciesNodeCollector(node.getDependency().getUsage());
            node.accept(visitor);
            Set<DependencyNode> nodes = visitor.getDependencies();
            for (DependencyNode currentNode : nodes) {
                postNodeWithChild(currentNode);
            }
            return true;
        } catch (URISyntaxException e) {
            new Neo4jDaoException("Bad URI", e);
        }
        return false;
    }

    private void postNodeWithChild(DependencyNode currentNode) throws URISyntaxException {
        Neo4jDependency dependency = postDependency(currentNode.getDependency());
        for (DependencyNode child : currentNode.getChildren()) {
            Neo4jDependency dependencyChild = postDependency(child.getDependency());
            executor.post(new Neo4jRelationship(dependency, dependencyChild, dependency.getUsage()));
        }
    }

    public String buildGremlinScript(Map<String, String> param) {
        StringBuilder str = new StringBuilder(100);
        str.append("g.V.outE('");
        str.append(Neo4jMetadataExtension.RELATIONSHIP_TYPE.getName());
        str.append("').inV{");
        List<String> strList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            StringBuilder strEntry = new StringBuilder(20);
            strEntry.append("it.");
            strEntry.append(entry.getKey());
            strEntry.append(" == '");
            strEntry.append(entry.getValue());
            strEntry.append("'");
            strList.add(strEntry.toString());
        }
        str.append(StringUtils.join(strList, " && "));
        str.append("}.inE.outV.inE.outV");
        return str.toString();
    }

}
