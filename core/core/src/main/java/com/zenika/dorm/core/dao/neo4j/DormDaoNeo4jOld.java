package com.zenika.dorm.core.dao.neo4j;


import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.exception.Neo4jDaoException;
import com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor;
import com.zenika.dorm.core.dao.neo4j.util.RequestExecutor;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.Usage;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoNeo4jOld implements DormDao {

    private static final Logger LOG = LoggerFactory.getLogger(DormDaoNeo4jOld.class);

    private static final TypeReference<List<Neo4jResponse<Neo4jDependency>>> TYPE_LIST_RESPONSE_DEPENDENCY = new TypeReference<List<Neo4jResponse<Neo4jDependency>>>() {
    };


    private RequestExecutor executor;
    private Neo4jIndex index;

    @Inject
    public DormDaoNeo4jOld() {
        this(new Neo4jRequestExecutor());
    }

    public DormDaoNeo4jOld(RequestExecutor executor) {
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


    public Neo4jDependency postDependency(Dependency dormDependency) {
        Usage usage = dormDependency.getUsage();
        Neo4jDependency dependency = null;
//        try {
//            dependency = new Neo4jDependency(dormDependency);
//            Neo4jMetadata metadata = dependency.getMetadata();
//            Neo4jMetadataExtension extension = dependency.getMetadata().getNeo4jExtension();
//            if (executor.get(dependency.getIndexURI(index), List.class).isEmpty()) {
//                extension.setResponse(executor.postExtension(MetadataExtensionMapper.fromExtension(extension.getExtension())));
//                executor.post(metadata);
//                executor.post(dependency);
//                executor.post(new Neo4jRelationship(metadata, extension, Neo4jMetadataExtension.RELATIONSHIP_TYPE));
//                executor.post(new Neo4jRelationship(dependency, metadata, Neo4jMetadata.RELATIONSHIP_TYPE));
//                executor.post(dependency, dependency.getIndexURI(index));
//            } else {
//                dependency = searchNode(dependency.getIndexURI(index),
//                        new TypeReference<List<Neo4jResponse<Neo4jDependency>>>() {
//                        }.getType());
//                fillNeo4jDependency(dependency, dormDependency.getMetadata());
//                dependency.setUsage(usage);
//            }
//        } catch (URISyntaxException e) {
//            throw new CoreException("URI syntax error", e);
//        }
        return dependency;
    }

    /**
     * d = g.addVertex();
     * m = g.addVertex([qualifier:'test',version:'1.0.0',type:'jar'])
     * e = g.addVertex([groupId:'test',artifactId:'test',version:'1.0.0',classifier:'bin',packaging:'jar',timestamp:'129099087'])
     * g.addEdge(d,m,'METADATA')
     * g.addEdge(m.e,'EXTENSION')
     */

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

    public Neo4jDependency fillNeo4jDependency(Neo4jDependency dependency, DormMetadata extensionPlug) {
//        try {
////            Neo4jRelationship dependencyMetadata = getSingleRelationship(dependency.getResponse()
////                    .getOutgoing_typed_relationships(Neo4jMetadata.RELATIONSHIP_TYPE));
////            Neo4jMetadata metadata = executor.getNode(dependencyMetadata.getEnd(), new TypeReference<Neo4jResponse<Neo4jMetadata>>() {
////            }.getType());
////            Neo4jRelationship metadataExtension = getSingleRelationship(metadata.getResponse()
////                    .getOutgoing_typed_relationships(Neo4jMetadataExtension.RELATIONSHIP_TYPE));
////            Neo4jMetadataExtension extension = executor.getExtension(metadataExtension.getEnd(), extensionPlug);
////            dependency.setMetadata(metadata);
////            metadata.setExtension(extension);
//            return dependency;
//        } catch (URISyntaxException e) {
//            throw new Neo4jDaoException("URI syntax exception", e);
//        }
        return null;
    }

    public Dependency getDependency(URI uri, Usage usage, DormMetadata extension) throws URISyntaxException {
        Neo4jDependency dependency = executor.getNode(uri, new TypeReference<Neo4jResponse<Neo4jDependency>>() {
        }.getType());
        return getDependency(dependency, usage, extension);
    }

    public Dependency getDependency(Neo4jDependency dependency, Usage usage, DormMetadata metadata) {
        dependency = fillNeo4jDependency(dependency, metadata);
        return DefaultDependency.create(metadata, usage);
    }

    private Neo4jRelationship getSingleRelationship(URI uri) {
        List<Neo4jRelationship> relationships = executor.get(uri, new TypeReference<List<Neo4jRelationship>>() {
        }.getType());
        return relationships.get(0);
    }

    public void putChild(Usage usage, Map<String, DependencyNode> dependencyNodeMap, List<Neo4jRelationship> relationships, DormMetadata extension) throws URISyntaxException {
//        for (Neo4jRelationship relationship : relationships) {
//            DependencyNode dependencyParent = dependencyNodeMap.get(relationship.getStart().toString());
//            DependencyNode dependencyChild = dependencyNodeMap.get(relationship.getEnd().toString());
//            if (dependencyParent == null) {
//                dependencyParent = DefaultDependencyNode.create(getDependency(relationship.getStart(), usage, extension));
//                dependencyNodeMap.put(relationship.getStart().toString(), dependencyParent);
//            }
//            if (dependencyChild == null) {
//                dependencyChild = DefaultDependencyNode.create(getDependency(relationship.getEnd(), usage, extension));
//                dependencyNodeMap.put(relationship.getEnd().toString(), dependencyChild);
//            }
//            dependencyParent.addChild(dependencyChild);
//        }
    }

    private void postNodeWithChild(DependencyNode currentNode) throws URISyntaxException {
//        Neo4jDependency dependency = postDependency(currentNode.getDependency());
//        for (DependencyNode child : currentNode.getChildren()) {
//            boolean isNotExist = true;
//            Neo4jDependency dependencyChild = postDependency(child.getDependency());
//            List<Neo4jRelationship> relationships = executor.getDependencyRelationship(dependencyChild.getResponse().getOutgoing_typed_relationships(dependencyChild.getUsage()));
//            for (Neo4jRelationship relationship : relationships) {
//                if (relationship.getStart().equals(dependency.getUri()) && relationship.getEnd().equals(dependencyChild.getUri())) {
//                    isNotExist = false;
//                    break;
//                }
//            }
//            if (isNotExist) {
//                executor.post(new Neo4jRelationship(dependency, dependencyChild, dependency.getUsage()));
//            }
//        }
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
    @Override
    public DormMetadata getMetadataByQualifier(String qualifier) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<DormMetadata> getMetadataByExtension(String extensionName, Map<String, String> extensionClauses, Usage usage) {
        return null;
    }

    @Override
    public void saveMetadata(DormMetadata metadata) {
    }
}