package com.zenika.dorm.maven.test.helper;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.builder.DormWebServiceRequestBuilder;
import com.zenika.dorm.core.test.helper.ExtensionFixtures;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import com.zenika.dorm.maven.model.impl.MavenConstant;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
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

        return new MavenMetadataBuilder(artifactId)
                .groupId(groupId)
                .version(mavenVersion)
                .extension(mavenType)
                .build();
    }

    public DormMetadataExtension getSnapshotMetadataExtension() {
        return new MavenMetadataBuilder(artifactId)
                .groupId(groupId)
                .version(mavenVersion)
                .snapshot(true)
                .extension("jar")
                .buildNumber("1")
                .timestamp("20110825.142212")
                .build();
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
    public DormWebServiceRequestBuilder getRequestBuilder() {
        return super.getRequestBuilder()
                .origin(origin)
                .property(MavenMetadataExtension.METADATA_GROUPID, groupId)
                .property(MavenMetadataExtension.METADATA_ARTIFACTID, artifactId)
                .property(MavenMetadataExtension.METADATA_VERSION, mavenVersion);
    }

    public DormWebServiceRequest getRequestWithFilename() {
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
        return DefaultDependency.create(getMetadata(), usage, getDormResource());
    }

    public Dependency getSnapshotDependencyWithResource() {
        Usage usage = Usage.createInternal(MavenMetadataExtension.EXTENSION_NAME);
        return DefaultDependency.create(DefaultDormMetadata.create(getRequestVersion(),
                getSnapshotMetadataExtension()), usage, getDormResource());
    }

    public MavenMetadataExtension getEntityExtension() {
        return new MavenMetadataBuilder(artifactId)
                .groupId(groupId)
                .version(mavenVersion)
                .build();
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
