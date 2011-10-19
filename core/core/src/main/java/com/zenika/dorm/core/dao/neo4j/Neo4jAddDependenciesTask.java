package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jWebResourceWrapper;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.visitor.impl.DependenciesNodeCollector;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jAddDependenciesTask extends Neo4jAbstractTask {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jAddDependenciesTask.class);

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
            LOG.debug("DormMetadata: " + node.getDependency().getMetadata());
            String parentUri = null;
            DormMetadata metadataParent = node.getDependency().getMetadata();

            Neo4jResponse responseParent = getResponse(metadataParent);

            if (responseParent == null) {
                parentUri = generateCreateRelationshipUri(saveMetadata(metadataParent).getId());
            } else {
                parentUri = responseParent.getCreate_relationship();
            }

            LOG.debug("DormMetadata Neo4j: " + parentUri);

            for (DependencyNode nodeChild : node.getChildren()) {
                DormMetadata metadataChild = nodeChild.getDependency().getMetadata();
                Neo4jResponse responseChild = getResponse(metadataChild);
                String childUri = null;
                Usage usage = nodeChild.getDependency().getUsage();
                if (responseChild == null) {
                    childUri = generateNodeUri(saveMetadata(metadataChild).getId());
                } else {
                    childUri = responseChild.getSelf();
                }
                Neo4jRelationship relationship = new Neo4jRelationship(
                        parentUri,
                        childUri,
                        usage.getName());
                LOG.debug("Relationship: " + relationship);
                saveRelationship(relationship);
            }
        }
        return root;
    }

    private Neo4jResponse getResponse(DormMetadata metadata) {
        DormBasicQuery query = new DormBasicQuery.Builder()
                .extensionName(metadata.getType())
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

//            LOG.debug("Response: " + response.getEntity(String.class));

        } catch (URISyntaxException e) {
            throw new CoreException("URI syntax exception", e);
        }
    }

    private DormMetadata saveMetadata(final DormMetadata metadata) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jWebResourceWrapper.class).toInstance(wrapper);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormMetadata.class).toInstance(metadata);
            }
        }).getInstance(Neo4jSinglePushTask.class).execute();
    }
}
