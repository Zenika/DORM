package com.zenika.dorm.maven.test.fixtures;

import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.model.builder.MavenMetadataUriBuilder;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFixtures {

    private MavenPathFixtures pathFixtures;

    private MavenMetadata simpleJar;
    private MavenMetadata simpleJarSha1;
    private MavenMetadata simpleJarMd5;
    private MavenMetadata simplePom;
    private MavenMetadata simplePomSha1;
    private MavenMetadata simplePomMd5;

    public MavenFixtures() {
        this(new MavenPathFixtures());
    }

    public MavenFixtures(MavenPathFixtures pathFixtures) {
        this.pathFixtures = pathFixtures;

        simpleJar = MavenMetadataUriBuilder.buildMavenMetadata(new MavenUri(pathFixtures.getJar()));
        simpleJarSha1 = MavenMetadataUriBuilder.buildMavenMetadata(new MavenUri(pathFixtures.getJarSha1Hash()));
        simpleJarMd5 = MavenMetadataUriBuilder.buildMavenMetadata(new MavenUri(pathFixtures.getJarMd5Hash()));
        simplePom = MavenMetadataUriBuilder.buildMavenMetadata(new MavenUri(pathFixtures.getPom()));
        simplePomSha1 = MavenMetadataUriBuilder.buildMavenMetadata(new MavenUri(pathFixtures.getPomSha1Hash()));
        simplePomMd5 = MavenMetadataUriBuilder.buildMavenMetadata(new MavenUri(pathFixtures.getPomMd5Hash()));
    }

    public MavenMetadata getSimpleJar() {
        return simpleJar;
    }

    public MavenMetadata getSimpleJarSha1() {
        return simpleJarSha1;
    }

    public MavenMetadata getSimpleJarMd5() {
        return simpleJarMd5;
    }

    public MavenMetadata getSimplePom() {
        return simplePom;
    }

    public MavenMetadata getSimplePomSha1() {
        return simplePomSha1;
    }

    public MavenMetadata getSimplePomMd5() {
        return simplePomMd5;
    }
}
