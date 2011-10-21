package com.zenika.dorm.maven.test.fixtures;

import com.zenika.dorm.maven.model.MavenPluginMetadata;
import com.zenika.dorm.maven.model.MavenUri;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFixtures {

    private MavenHttpPathFixtures httpPathFixtures;

    private MavenPluginMetadata simpleJar;
    private MavenPluginMetadata simpleJarSha1;
    private MavenPluginMetadata simpleJarMd5;
    private MavenPluginMetadata simplePom;
    private MavenPluginMetadata simplePomSha1;
    private MavenPluginMetadata simplePomMd5;

    public MavenFixtures() {
        this(new MavenHttpPathFixtures());
    }

    public MavenFixtures(MavenHttpPathFixtures httpPathFixtures) {
        this.httpPathFixtures = httpPathFixtures;

        simpleJar = new MavenUri(httpPathFixtures.getJar()).toMavenPlugin();
        simpleJarSha1 = new MavenUri(httpPathFixtures.getJarSha1Hash()).toMavenPlugin();
        simpleJarMd5 = new MavenUri(httpPathFixtures.getJarMd5Hash()).toMavenPlugin();
        simplePom = new MavenUri(httpPathFixtures.getPom()).toMavenPlugin();
        simplePomSha1 = new MavenUri(httpPathFixtures.getPomSha1Hash()).toMavenPlugin();
        simplePomMd5 = new MavenUri(httpPathFixtures.getPomMd5Hash()).toMavenPlugin();
    }

    public MavenPluginMetadata getSimpleJar() {
        return simpleJar;
    }

    public MavenPluginMetadata getSimpleJarSha1() {
        return simpleJarSha1;
    }

    public MavenPluginMetadata getSimpleJarMd5() {
        return simpleJarMd5;
    }

    public MavenPluginMetadata getSimplePom() {
        return simplePom;
    }

    public MavenPluginMetadata getSimplePomSha1() {
        return simplePomSha1;
    }

    public MavenPluginMetadata getSimplePomMd5() {
        return simplePomMd5;
    }
}
