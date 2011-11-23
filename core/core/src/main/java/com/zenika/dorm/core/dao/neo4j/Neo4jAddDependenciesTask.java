package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.graph.visitor.impl.DependenciesNodeCollector;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jAddDependenciesTask extends Neo4jAbstractTask {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jAddDependenciesTask.class);

    @Inject
    private DependencyNode root;

    @Override
    public DependencyNode execute() {

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
                String childUri;
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
                neo4jService.createRelationship(relationship);
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

    private DormMetadata saveMetadata(final DormMetadata metadata) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Neo4jService.class).toInstance(neo4jService);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormMetadata.class).toInstance(metadata);
            }
        }).getInstance(Neo4jSinglePushTask.class).execute();
    }
}
