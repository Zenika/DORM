package com.zenika.dorm.core.test.model;

import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormMetadataTest extends DormMetadata {

    public static final String EXTENSION_NAME = "DormTest";
    public static final String VERSION_FIELD = "version";
    public static final String ARTIFACT_ID = "artifactId";
    public static final String GROUP_ID = "groupId";
    public static final String AUTHOR_ID = "author";

    private String version;

    private String artifactId;
    private String groupId;
    private String author;

    public DormMetadataTest(String version, String artifactId) {
        super(null);
        this.version = version;
        this.artifactId = artifactId;
    }

    public DormMetadataTest(Long id, String version, String artifactId) {
        super(id);
        this.version = version;
        this.artifactId = artifactId;
    }

    public DormMetadataTest(String version, String artifactId, String groupId, String author) {
        this.version = version;
        this.artifactId = artifactId;
        this.groupId = groupId;
        this.author = author;
    }

    public static DormMetadata getDefault() {
        return new DormMetadataTest("1.0.0", "property");
    }

    @Override
    public String getName() {
        return artifactId;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getType() {
        return EXTENSION_NAME;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DormMetadataTest)) return false;

        DormMetadataTest that = (DormMetadataTest) o;

        if (artifactId != null ? !artifactId.equals(that.artifactId) : that.artifactId != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DormMetadataTest");
        sb.append("{id='").append(id).append('\'');
        sb.append(", extensionName='").append(getType()).append('\'');
        sb.append(", fields='").append(artifactId).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
