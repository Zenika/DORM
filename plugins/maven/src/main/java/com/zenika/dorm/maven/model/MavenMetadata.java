package com.zenika.dorm.maven.model;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.util.DormStringUtils;
import com.zenika.dorm.maven.exception.MavenException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Maven immutable extension point to the dorm model
 * Add maven specific metadatas
 */
public final class MavenMetadata extends DormMetadata {

    public static final transient String EXTENSION_NAME = "maven";

    /**
     * Metadata names
     */
    public static final String METADATA_GROUPID = "groupId";
    public static final String METADATA_ARTIFACTID = "artifactId";
    public static final String METADATA_VERSION = "version";
    public static final String METADATA_SNAPSHOT = "snapshot";

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final boolean snapshot;

    private final MavenBuildInfo buildInfo;

    public MavenMetadata(Long id, String groupId, String artifactId, String version, MavenBuildInfo buildInfo) {

        super(id);

        if (DormStringUtils.oneIsBlank(groupId, artifactId, version)) {
            throw new MavenException("Following metadatas are required : groupId, artifactId, versionId");
        }

        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.buildInfo = buildInfo;

        snapshot = (version.endsWith("-" + MavenConstant.Special.SNAPSHOT)) ? true : false;
    }

    @Override
    public String getName() {

        String separator = ":";

        StringBuilder qualifier = new StringBuilder()
                .append(groupId).append(separator)
                .append(artifactId);

        if (StringUtils.isNotBlank(buildInfo.getTimestamp()) && StringUtils.isNotBlank(buildInfo.getBuildNumber())) {
            qualifier.append(separator).append(buildInfo.getTimestamp())
                    .append(separator).append(buildInfo.getBuildNumber());
        }

        if (StringUtils.isNotBlank(buildInfo.getClassifier())) {
            qualifier.append(separator).append(buildInfo.getClassifier());
        }

        return qualifier
                .append(separator).append(buildInfo.getExtension())
                .toString();
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getExtensionName() {
        return EXTENSION_NAME;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public MavenBuildInfo getBuildInfo() {
        return buildInfo;
    }

    public boolean isSnapshot() {
        return snapshot;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("groupId", groupId)
                .append("artifactId", artifactId)
                .append("version", version)
                .append("snapshot", snapshot)
                .append("buildInfo", buildInfo)
                .appendSuper(super.toString())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MavenMetadata metadata = (MavenMetadata) o;

        if (snapshot != metadata.snapshot) return false;
        if (artifactId != null ? !artifactId.equals(metadata.artifactId) : metadata.artifactId != null) return false;
        if (buildInfo != null ? !buildInfo.equals(metadata.buildInfo) : metadata.buildInfo != null) return false;
        if (groupId != null ? !groupId.equals(metadata.groupId) : metadata.groupId != null) return false;
        if (version != null ? !version.equals(metadata.version) : metadata.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (snapshot ? 1 : 0);
        result = 31 * result + (buildInfo != null ? buildInfo.hashCode() : 0);
        return result;
    }
}
