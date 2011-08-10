package com.zenika.dorm.maven.test.helper;

import com.zenika.dorm.core.model.*;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.*;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.test.helper.ExtensionFixtures;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFixtures extends ExtensionFixtures {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFixtures.class);

    /**
     * Maven metadata extension
     */
    private String groupId = "testgroup1.testgroup2";
    private String artifactId = "testartifact";
    private String mavenVersion = "1.0-SNAPSHOT";
    private String type = "jar";
    private String origin = MavenMetadataExtension.EXTENSION_NAME;

    @Override
    public DormMetadataExtension getMetadataExtension() {
        return new MavenMetadataExtension(groupId, artifactId, mavenVersion, type);
    }

    @Override
    public String getRequestVersion() {
        return mavenVersion;
    }

    @Override
    public DormRequestBuilder getRequestBuilder() {
        return super.getRequestBuilder()
                .origin(origin)
                .property(MavenMetadataExtension.METADATA_GROUPID, groupId)
                .property(MavenMetadataExtension.METADATA_ARTIFACTID, artifactId)
                .property(MavenMetadataExtension.METADATA_VERSION, mavenVersion);
    }

    public DormRequest getRequestWithType() {
        return getRequestBuilder()
                .property(MavenMetadataExtension.METADATA_TYPE, type)
                .build();
    }

    /**
     * Create a maven dependency
     * All maven dependency must have the usage internal because of top generic "entity" dependency
     *
     * @return
     */
    @Override
    public Dependency getDependencyWithFile() {

        Usage usage = Usage.createInternal(MavenMetadataExtension.EXTENSION_NAME);
        LOG.trace("Maven dependency fixture has the internal usage = " + usage);

        return DefaultDependency.create(getMetadata(), usage, getDormFile());
    }

    public MavenMetadataExtension getEntityExtension() {
        return new MavenMetadataExtension(groupId, artifactId, mavenVersion,
                MavenProcessor.ENTITY_TYPE);
    }

    public DormMetadata getEntityMetadata() {
        return DefaultDormMetadata.create(mavenVersion, getEntityExtension());
    }

    /**
     * Entity dependency has no file
     *
     * @return
     */
    public Dependency getEntityDependency() {
        return DefaultDependency.create(getEntityMetadata());
    }

    public DependencyNode getEntityNodeWithChild() {
        DependencyNode node = DefaultDependencyNode.create(getEntityDependency());
        node.addChild(getNodeWithFile());
        return node;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getMavenVersion() {
        return mavenVersion;
    }

    public String getType() {
        return type;
    }

    public String getOrigin() {
        return origin;
    }
}
