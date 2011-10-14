package com.zenika.dorm.maven.model;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.constant.MavenConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maven immutable extension point to the dorm model
 * Add maven specific metadatas
 */
public final class MavenMetadata extends DormMetadata {

    public static final String EXTENSION_NAME = "maven";

    public static final String USER_AGENT = "maven-agent";

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

        this.groupId = checkNotNull(groupId);
        this.artifactId = checkNotNull(artifactId);
        this.version = checkNotNull(version);

        this.buildInfo = buildInfo;

        snapshot = (version.endsWith("-" + MavenConstant.Special.SNAPSHOT)) ? true : false;
    }

    @Override
    public String getName() {

        String separator = ":";

        StringBuilder qualifier = new StringBuilder()
                .append(groupId).append(separator)
                .append(artifactId);

        if (null != buildInfo) {

            if (StringUtils.isNotBlank(buildInfo.getTimestamp()) && StringUtils.isNotBlank(buildInfo.getBuildNumber())) {
                qualifier.append(separator).append(buildInfo.getTimestamp())
                        .append(separator).append(buildInfo.getBuildNumber());
            }

            if (StringUtils.isNotBlank(buildInfo.getClassifier())) {
                qualifier.append(separator).append(buildInfo.getClassifier());
            }

            qualifier.append(separator).append(buildInfo.getExtension());
        }

        return qualifier.toString();
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getType() {
        return EXTENSION_NAME;
    }

    public boolean equalsEntityMetadataOnly(MavenMetadata metadata) {

        if (this == metadata) return true;
        if (metadata == null) return false;

        if (snapshot != metadata.snapshot) return false;
        if (artifactId != null ? !artifactId.equals(metadata.artifactId) : metadata.artifactId != null) return false;
        if (groupId != null ? !groupId.equals(metadata.groupId) : metadata.groupId != null) return false;
        if (version != null ? !version.equals(metadata.version) : metadata.version != null) return false;

        return true;
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

    public boolean isMavenMetadata() {

        return StringUtils.equals(uri.getFilename().getFilename(), MavenConstant.Special.MAVEN_METADATA_XML);
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
