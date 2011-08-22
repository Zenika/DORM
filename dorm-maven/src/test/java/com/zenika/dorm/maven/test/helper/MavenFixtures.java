package com.zenika.dorm.maven.test.helper;

import com.zenika.dorm.core.model.*;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.test.helper.ExtensionFixtures;
import com.zenika.dorm.maven.model.impl.MavenConstant;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtensionBuilder;
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
    private String mavenType = MavenConstant.FileExtension.JAR;
    private String mavenFilename = artifactId + "." + mavenType;
    private String origin = MavenMetadataExtension.EXTENSION_NAME;

    @Override
    public DormMetadataExtension getMetadataExtension() {
        return new MavenMetadataExtensionBuilder(groupId, artifactId, mavenVersion).build();
    }

    @Override
    public String getRequestVersion() {
        return mavenVersion;
    }

    @Override
    public String getType() {
        return mavenType;
    }

    @Override
    public DormRequestBuilder getRequestBuilder() {
        return super.getRequestBuilder()
                .origin(origin)
                .property(MavenMetadataExtension.METADATA_GROUPID, groupId)
                .property(MavenMetadataExtension.METADATA_ARTIFACTID, artifactId)
                .property(MavenMetadataExtension.METADATA_VERSION, mavenVersion);
    }

    public DormRequest getRequestWithFilename() {
        return getRequestBuilder()
                .filename(mavenFilename)
                .build();
    }

    /**
     * Create a maven dependency
     * All maven dependency must have the usage internal because of top generic "entity" dependency
     *
     * @return
     */
    @Override
    public Dependency getDependencyWithResource() {

        Usage usage = Usage.createInternal(MavenMetadataExtension.EXTENSION_NAME);
        LOG.trace("Maven dependency fixture has the internal usage = " + usage);

        return DefaultDependency.create(getMetadata(), usage, getDormResource());
    }

    public MavenMetadataExtension getEntityExtension() {
        return new MavenMetadataExtensionBuilder(groupId, artifactId, mavenVersion).build();
    }

    public DormMetadata getEntityMetadata() {
        return DefaultDormMetadata.create(mavenVersion, MavenProcessor.ENTITY_TYPE, getEntityExtension());
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
        node.addChild(getNodeWithResource());
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

    public String getOrigin() {
        return origin;
    }
}
