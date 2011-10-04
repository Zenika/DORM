package com.zenika.dorm.maven.model;

import com.zenika.dorm.maven.constant.MavenConstant;
import com.zenika.dorm.maven.exception.MavenException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenUri {

    private String uri;

    private String groupId;
    private String artifactId;
    private String version;
    private boolean snapshot;

    private MavenFilename filename;

    public MavenUri(String uri) {
        this.uri = uri;
        extractFields();
    }

    private void extractFields() {

        if (StringUtils.isBlank(uri)) {
            throw new MavenException("Maven uri is required");
        }

        String[] pathParams = uri.split("/");
        int paramsNumber = pathParams.length;

        version = pathParams[paramsNumber - 2];
        if (version.endsWith("-" + MavenConstant.Special.SNAPSHOT)) {
            snapshot = true;
        }

        artifactId = pathParams[paramsNumber - 3];

        int groupidParam = paramsNumber - 4;
        groupId = pathParams[groupidParam];

        for (int i = groupidParam - 1; i >= 0; i--) {
            groupId = pathParams[i] + "." + groupId;
        }

        this.filename = new MavenFilename(this, pathParams[paramsNumber - 1]);
    }

    /**
     * Remove "-SNAPSHOT" from version if exists
     *
     * @return
     */
    public String getVersionWithtoutSnapshot() {

        if (!snapshot) {
            return version;
        }

        return version.substring(0, version.length() - (MavenConstant.Special.SNAPSHOT.length() + 1));
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

    public MavenFilename getFilename() {
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
                .append("snapshot", snapshot)
                .append("filename", filename)
                .appendSuper(super.toString())
                .toString();
    }
}
