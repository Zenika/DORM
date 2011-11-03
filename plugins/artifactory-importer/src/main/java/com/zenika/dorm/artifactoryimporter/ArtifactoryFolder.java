package com.zenika.dorm.artifactoryimporter;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ArtifactoryFolder {

    private String uri;
    private String metadataUri;
    private String repository;
    private String path;
    private Date created;
    private String createdBy;
    private Date lastModified;
    private String modifiedBy;
    private Date lastUpdated;
    private List<ArtifactoryFile> children;

    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMetadataUri() {
        return metadataUri;
    }

    @JsonProperty("metadataUri")
    public void setMetadataUri(String metadataUri) {
        this.metadataUri = metadataUri;
    }

    public String getRepository() {
        return repository;
    }

    @JsonProperty("repo")
    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<ArtifactoryFile> getChildren() {
        return children;
    }

    @JsonProperty("children")
    public void setChildren(List<ArtifactoryFile> children) {
        this.children = children;
    }

    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    @JsonProperty("lastModified")
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    @JsonProperty("modifiedBy")
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @JsonProperty("lastUpdated")
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ArtifactoryFolder");
        sb.append("{children=").append(children);
        sb.append(", created=").append(created);
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", lastModified=").append(lastModified);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append(", metadataUri='").append(metadataUri).append('\'');
        sb.append(", modifiedBy='").append(modifiedBy).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", repository='").append(repository).append('\'');
        sb.append(", uri='").append(uri).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
