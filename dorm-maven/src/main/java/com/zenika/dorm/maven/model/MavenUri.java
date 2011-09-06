package com.zenika.dorm.maven.model;

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
    private String extension;

    public MavenUri(String uri) {
        this.uri = uri;
        extractFields();
    }

    private void extractFields() {

        extension = FilenameUtils.getExtension(uri);

        String[] pathParams = FilenameUtils.removeExtension(uri).split("/");
        int paramsNumber = pathParams.length;

        filename = pathParams[paramsNumber - 1];
        version = pathParams[paramsNumber - 2];
        artifactId = pathParams[paramsNumber - 3];

        int groupidParam = paramsNumber - 4;
        groupId = pathParams[groupidParam];

        for (int i = 0; i < groupidParam; i++) {
            groupId = pathParams[i] + "/" + groupId;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("url", uri)
                .append("groupId", groupId)
                .append("artifactId", artifactId)
                .append("version", version)
                .append("filename", filename)
                .append("extension", extension)
                .appendSuper(super.toString())
                .toString();
    }
}
