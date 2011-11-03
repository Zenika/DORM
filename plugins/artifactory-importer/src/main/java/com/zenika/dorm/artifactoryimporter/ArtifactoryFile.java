package com.zenika.dorm.artifactoryimporter;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ArtifactoryFile {

    private String uri;
    private boolean folder;

    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isFolder() {
        return folder;
    }

    @JsonProperty("folder")
    public void setFolder(boolean folder) {
        this.folder = folder;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ArtifactoryFile");
        sb.append("{folder=").append(folder);
        sb.append(", uri='").append(uri).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
