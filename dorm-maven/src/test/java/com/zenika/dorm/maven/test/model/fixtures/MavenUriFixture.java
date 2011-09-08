package com.zenika.dorm.maven.test.model.fixtures;

import com.zenika.dorm.maven.model.MavenUri;
import org.fest.assertions.Assertions;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenUriFixture {

    private String groupId;
    private String artifactId;
    private String version;
    private boolean snapshot;

    private MavenUri uri;

    public MavenUriFixture(String uri) {
        this.uri = new MavenUri(uri);
    }

    public MavenUriFixture groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public MavenUriFixture artifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public MavenUriFixture version(String version) {
        this.version = version;
        return this;
    }

    public MavenUriFixture snapshot(boolean snapshot) {
        this.snapshot = snapshot;
        return this;
    }

    public void compare() {
        Assertions.assertThat(uri.getArtifactId()).isEqualTo(artifactId);
        Assertions.assertThat(uri.getGroupId()).isEqualTo(groupId);
        Assertions.assertThat(uri.getVersion()).isEqualTo(version);
        Assertions.assertThat(uri.isSnapshot()).isEqualTo(snapshot);
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

    public boolean isSnapshot() {
        return snapshot;
    }

    public MavenUri getUri() {
        return uri;
    }
}
