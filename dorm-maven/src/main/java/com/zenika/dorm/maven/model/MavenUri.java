package com.zenika.dorm.maven.model;

import com.zenika.dorm.maven.model.impl.MavenConstant;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenUri {

    private String uri;

    private String groupId;
    private String artifactId;
    private String version;
    private String filename;
    private boolean snapshot;

    public MavenUri(String uri) {
        this.uri = uri;
        extractFields();
    }

    private void extractFields() {

        String[] pathParams = FilenameUtils.removeExtension(uri).split("/");
        int paramsNumber = pathParams.length;

        filename = pathParams[paramsNumber - 1];

        version = pathParams[paramsNumber - 2];
        if (version.endsWith("-" + MavenConstant.Special.SNAPSHOT)) {
            snapshot = true;
        }

        artifactId = pathParams[paramsNumber - 3];

        int groupidParam = paramsNumber - 4;
        groupId = pathParams[groupidParam];

        for (int i = groupidParam - 1; i >= 0; i--) {
            groupId = pathParams[i] + "/" + groupId;
        }
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
        extractFields();
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

    public String getFilename() {
        return filename;
    }

    public boolean isSnapshot() {
        return snapshot;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uri", uri)
                .append("groupId", groupId)
                .append("artifactId", artifactId)
                .append("version", version)
                .append("filename", filename)
                .append("snapshot", snapshot)
                .appendSuper(super.toString())
                .toString();
    }
}
