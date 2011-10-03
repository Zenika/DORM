package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.visitor.DependencyVisitor;
import com.zenika.dorm.core.graph.visitor.impl.DependenciesNodeCollector;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import org.neo4j.graphdb.Relationship;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jAddDependenciesTask extends Neo4jAbstractTask {

    @Inject
    private DependencyNode root;

    private WebResource resource;

    @Override
    public DependencyNode execute() {
        resource = wrapper.get();

        DependenciesNodeCollector visitor = new DependenciesNodeCollector(root.getDependency().getUsage());
        root.accept(visitor);
        Set<DependencyNode> dependencyNodes = visitor.getDependencies();

        for (DependencyNode node : dependencyNodes) {
            DormMetadata metadataParent = node.getDependency().getMetadata();

            Neo4jResponse responseParent = getResponse(metadataParent);

            Usage usage = node.getDependency().getUsage(); // ??????????????????????????????????????????????????????????
            for (DependencyNode nodeChild : node.getChildren()) {
                DormMetadata metadataChild = nodeChild.getDependency().getMetadata();
                Neo4jResponse responseChild = getResponse(metadataChild);
                Neo4jRelationship relationship = new Neo4jRelationship(
                        responseParent.getCreate_relationship(),
                        responseChild.getSelf(),
                        usage.getName());
                saveRelationship(relationship);
            }
        }
        return root;
    }

    private Neo4jResponse getResponse(DormMetadata metadata) {
        DormBasicQuery query = new DormBasicQuery.Builder()
                .extensionName(metadata.getExtensionName())
                .name(metadata.getName())
                .version(metadata.getVersion())
                .build();

        return getMetadata(query);
    }

    private void saveRelationship(Neo4jRelationship relationship) {
        try {
            URI createUri = new URI(relationship.getFrom());

            resource.uri(createUri)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(relationship)
                    .post();

        } catch (URISyntaxException e) {
            throw new CoreException("URI syntax exception", e);
        }
    }
}
