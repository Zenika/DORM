package com.zenika.dorm.maven.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenIdentifier {

    private MavenUri uri;
    private MavenFilename filename;

    public MavenIdentifier(String uri) {
        this.uri = new MavenUri(uri);
        this.filename = new MavenFilename(this.uri);
    }

    public MavenIdentifier(MavenUri uri, MavenFilename filename) {
        this.uri = uri;
        this.filename = filename;
    }

    public MavenUri getUri() {
        return uri;
    }

    public MavenFilename getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uri", uri)
                .append("filename", filename)
                .appendSuper(super.toString())
                .toString();
    }
}
