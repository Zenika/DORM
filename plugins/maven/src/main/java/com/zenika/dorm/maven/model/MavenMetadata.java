package com.zenika.dorm.maven.model;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.util.DormStringUtils;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * Maven immutable extension point to the dorm model
 * Add maven specific metadatas
 */
public final class MavenMetadata implements DormMetadataExtension {

    public static final transient String EXTENSION_NAME = "maven";

    /**
     * Metadata names
     */
    public static final String METADATA_GROUPID = "groupId";
    public static final String METADATA_ARTIFACTID = "artifactId";
    public static final String METADATA_VERSION = "version";
    public static final String METADATA_CLASSIFIER = "classifier";
    public static final String METADATA_TIMESTAMP = "timestamp";
    public static final String METADATA_EXTENSION = "extension";
    public static final String METADATA_BUILDNUMBER = "buildNumber";
    public static final String METADATA_SNAPSHOT = "snapshot";

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String classifier;
    private final String timestamp;
    private final String extension;
    private final String buildNumber;
    private final boolean snapshot;

    public MavenMetadata(String groupId, String artifactId, String version, String extension,
                         String classifier, String timestamp, String buildNumber,
                         String url, boolean mavenMetadata, boolean snapshot) {

        if (DormStringUtils.oneIsBlank(groupId, artifactId, version)) {
            throw new MavenException("Following metadatas are required : groupId, artifactId, versionId");
        }

        if (!mavenMetadata && !MavenConstant.FileExtension.isMavenExtension(extension)) {
            throw new MavenException("Extension is not allowed for a maven file : " + extension);
        }

        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;

        this.extension = StringUtils.defaultIfBlank(extension, "");
        this.classifier = StringUtils.defaultIfBlank(classifier, "");
        this.timestamp = StringUtils.defaultIfBlank(timestamp, "");
        this.buildNumber = StringUtils.defaultIfBlank(buildNumber, "");

        this.snapshot = snapshot;
    }

    @Override
    public String getQualifier() {

        String separator = ":";

        StringBuilder qualifier = new StringBuilder()
                .append(groupId).append(separator)
                .append(artifactId).append(separator)
                .append(version);

        if (!StringUtils.isNotBlank(timestamp) && !StringUtils.isNotBlank(buildNumber)) {
            qualifier.append(separator).append(timestamp)
                    .append(separator).append(buildNumber);
        }

        if (StringUtils.isNotBlank(classifier)) {
            qualifier.append(separator).append(classifier);
        }

        return qualifier
                .append(separator).append(extension)
                .toString();
    }

    @Override
    public String getExtensionName() {
        return EXTENSION_NAME;
    }

    /**
     * Complete metadata contains groupdid and other attributes,
     * Not complete contains only url
     *
     * @return
     */
    public boolean isComplete() {
        return !DormStringUtils.oneIsBlank(groupId, artifactId, version);
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getExtension() {
        return extension;
    }

    public String getClassifier() {
        return classifier;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBuildNumber() {

        return buildNumber;
    }

    public boolean isSnapshot() {
        return snapshot;
    }

    @Override
    public DormMetadataExtension createFromMap(Map<String, String> properties) {
        return new MavenMetadataBuilder(properties.get(METADATA_ARTIFACTID))
                .groupId(properties.get(METADATA_GROUPID))
                .version(properties.get(METADATA_VERSION))
                .classifier(properties.get(METADATA_CLASSIFIER))
                .extension(properties.get(METADATA_EXTENSION))
                .snapshot(Boolean.valueOf(properties.get(METADATA_SNAPSHOT)))
                .timestamp(properties.get(METADATA_TIMESTAMP))
                .buildNumber(properties.get(METADATA_BUILDNUMBER))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MavenMetadata extension1 = (MavenMetadata) o;

        if (snapshot != extension1.snapshot) return false;
        if (artifactId != null ? !artifactId.equals(extension1.artifactId) : extension1.artifactId != null)
            return false;
        if (buildNumber != null ? !buildNumber.equals(extension1.buildNumber) : extension1.buildNumber != null)
            return false;
        if (classifier != null ? !classifier.equals(extension1.classifier) : extension1.classifier != null)
            return false;
        if (extension != null ? !extension.equals(extension1.extension) : extension1.extension != null)
            return false;
        if (groupId != null ? !groupId.equals(extension1.groupId) : extension1.groupId != null) return false;
        if (timestamp != null ? !timestamp.equals(extension1.timestamp) : extension1.timestamp != null)
            return false;
        if (version != null ? !version.equals(extension1.version) : extension1.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (classifier != null ? classifier.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        result = 31 * result + (buildNumber != null ? buildNumber.hashCode() : 0);
        result = 31 * result + (snapshot ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("groupId", groupId)
                .append("artifactId", artifactId)
                .append("version", version)
                .append("classifier", classifier)
                .append("timestamp", timestamp)
                .append("extension", extension)
                .append("buildNumber", buildNumber)
                .append("snapshot", snapshot)
                .appendSuper(super.toString())
                .toString();
    }
}
