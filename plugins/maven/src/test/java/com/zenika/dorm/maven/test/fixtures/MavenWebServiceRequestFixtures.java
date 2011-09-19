package com.zenika.dorm.maven.test.fixtures;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.maven.exception.MavenException;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenWebServiceRequestFixtures {

    private static final String BASE_PATH = "src/test/resources/samples";
    private static final String FILENAME = "commons-io-2.0.1";

    private MavenPathFixtures pathFixtures;

    private File jar;
    private File jarSha1;
    private File jarMd5;
    private File pom;
    private File pomSha1;
    private File pomMd5;
    private File mavenMetadataXml;

    private DormWebServiceRequest simpleJar;
    private DormWebServiceRequest simpleJarSha1;
    private DormWebServiceRequest simpleJarMd5;
    private DormWebServiceRequest simplePom;
    private DormWebServiceRequest simplePomSha1;
    private DormWebServiceRequest simplePomMd5;
    private DormWebServiceRequest simpleMavenMetadata;

    public MavenWebServiceRequestFixtures() {
        this(new MavenPathFixtures());
    }

    public MavenWebServiceRequestFixtures(MavenPathFixtures pathFixtures) {
        this.pathFixtures = pathFixtures;

        getFiles();

        simpleJar = new DormWebServiceRequest.Builder("maven")
                .property("uri", pathFixtures.getJar())
                .file(jar)
                .build();

        simpleJarSha1 = new DormWebServiceRequest.Builder("maven")
                .property("uri", pathFixtures.getJarSha1Hash())
                .file(jarSha1)
                .build();

        simpleJarMd5 = new DormWebServiceRequest.Builder("maven")
                .property("uri", pathFixtures.getJarMd5Hash())
                .file(jarMd5)
                .build();

        simplePom = new DormWebServiceRequest.Builder("maven")
                .property("uri", pathFixtures.getPom())
                .file(pom)
                .build();

        simplePomSha1 = new DormWebServiceRequest.Builder("maven")
                .property("uri", pathFixtures.getPomSha1Hash())
                .file(pomSha1)
                .build();

        simplePomMd5 = new DormWebServiceRequest.Builder("maven")
                .property("uri", pathFixtures.getPomMd5Hash())
                .file(pomMd5)
                .build();

        simpleMavenMetadata = new DormWebServiceRequest.Builder("maven")
                .property("uri", pathFixtures.getMavenMetadataXml())
                .file(mavenMetadataXml)
                .build();
    }

    private void getFiles() {

        jar = new File(BASE_PATH + "/" + FILENAME + ".jar");
        jarSha1 = new File(BASE_PATH + "/" + FILENAME + ".jar.sha1");
        jarMd5 = new File(BASE_PATH + "/" + FILENAME + ".jar.md5");
        pom = new File(BASE_PATH + "/" + FILENAME + ".pom");
        pomSha1 = new File(BASE_PATH + "/" + FILENAME + ".pom.sha1");
        pomMd5 = new File(BASE_PATH + "/" + FILENAME + ".pom.md5");
        mavenMetadataXml = new File(BASE_PATH + "/maven-metadata.xml");

        if (null == jar || null == jarSha1 || null == jarMd5 || null == pom || null == pomSha1 ||
                null == pomMd5 || null == mavenMetadataXml) {
            throw new MavenException("Sample files are null");
        }
    }

    public DormWebServiceRequest getSimpleJar() {
        return simpleJar;
    }

    public DormWebServiceRequest getSimpleJarSha1() {
        return simpleJarSha1;
    }

    public DormWebServiceRequest getSimpleJarMd5() {
        return simpleJarMd5;
    }

    public DormWebServiceRequest getSimplePom() {
        return simplePom;
    }

    public DormWebServiceRequest getSimplePomSha1() {
        return simplePomSha1;
    }

    public DormWebServiceRequest getSimplePomMd5() {
        return simplePomMd5;
    }

    public DormWebServiceRequest getSimpleMavenMetadata() {
        return simpleMavenMetadata;
    }
}
