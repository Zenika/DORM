package com.zenika.dorm.maven.model.impl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.maven.exception.MavenException;

import java.util.Map;

/**
 * Maven immutable extension point to the dorm model
 * Add maven specific metadatas
 *
 * todo: add packaging + classifier metadatas
 */
public final class MavenMetadataExtension implements DormMetadataExtension {

    public static final String EXTENSION_NAME = "maven";

    /**
     * Metadata names
     */
    public static final String METADATA_GROUPID = "groupId";
    public static final String METADATA_ARTIFACTID = "artifactId";
    public static final String METADATA_VERSION = "version";
    public static final String METADATA_CLASSIFIER = "classifier";
    public static final String METADATA_PACKAGING = "packaging";
    public static final String METADATA_TIMESTAMP = "timestamp";
    public static final String MAVEN_METADATA_XML = "maven-metadata.xml";

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String classifier;
    private final String packaging;
    private final String timestamp;
    private final boolean mavenMetadata;

    /**
     * Only accessed by the builder in the same package
     *
     * @param groupId
     * @param artifactId
     * @param version
     * @param packaging
     * @param classifier
     * @param timestamp
     */
    MavenMetadataExtension(String groupId, String artifactId, String version, String packaging,
                           String classifier, String timestamp, Boolean mavenMetadata) {

        if (null == groupId || null == artifactId || null == version) {
            throw new MavenException("Following metadatas are required : groupId, artifactId, versionId");
        }

        if (null == packaging) {
            packaging = "jar";
        }

        if (null == classifier) {
            classifier = "";
        }

        if (null == timestamp) {
            timestamp = "";
        }

        if (null == mavenMetadata) {
            mavenMetadata = false;
        }

        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.packaging = packaging;

        this.mavenMetadata = mavenMetadata;
        this.classifier = classifier;
        this.timestamp = timestamp;
    }

    @Override
    public String getQualifier() {
        String separator = ":";
        StringBuilder qualifier = new StringBuilder()
                .append(groupId).append(separator)
                .append(artifactId).append(separator)
                .append(packaging).append(separator);

        if (!classifier.isEmpty()) {
            qualifier.append(classifier).append(separator);
        }

        if (!timestamp.isEmpty()) {
            qualifier.append(timestamp).append(separator);
        }

        return qualifier.append(packaging).append(separator)
                .append(version).toString();
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

    public String getVersion() {
        return version;
    }

    public String getPackaging() {
        return packaging;
    }

    public String getClassifier() {
        return classifier;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean isMavenMetadata() {
        return mavenMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MavenMetadataExtension extension = (MavenMetadataExtension) o;

        if (artifactId != null ? !artifactId.equals(extension.artifactId) : extension.artifactId != null)
            return false;
        if (classifier != null ? !classifier.equals(extension.classifier) : extension.classifier != null)
            return false;
        if (groupId != null ? !groupId.equals(extension.groupId) : extension.groupId != null) return false;
        if (packaging != null ? !packaging.equals(extension.packaging) : extension.packaging != null)
            return false;
        if (timestamp != null ? !timestamp.equals(extension.timestamp) : extension.timestamp != null)
            return false;
        if (version != null ? !version.equals(extension.version) : extension.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (classifier != null ? classifier.hashCode() : 0);
        result = 31 * result + (packaging != null ? packaging.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    @Override
    public DormMetadataExtension createFromMap(Map<String, String> properties) {
        return new MavenMetadataExtension(properties.get(METADATA_GROUPID),
                properties.get(METADATA_ARTIFACTID), properties.get(METADATA_VERSION),
                properties.get(METADATA_PACKAGING), properties.get(METADATA_CLASSIFIER),
                properties.get(METADATA_TIMESTAMP), null);
    }


}
