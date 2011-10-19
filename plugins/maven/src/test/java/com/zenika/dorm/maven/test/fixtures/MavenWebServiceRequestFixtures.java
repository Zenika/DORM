package com.zenika.dorm.maven.test.fixtures;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.util.DormFileUtils;
import com.zenika.dorm.maven.exception.MavenException;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenWebServiceRequestFixtures {

    private static final String BASE_PATH = "/samples/commons-io/commons-io-2.0.1";

    private MavenPathFixtures pathFixtures;

    private File jar;
    private File jarSha1;
    private File jarMd5;
    private File pom;
    private File pomSha1;
    private File pomMd5;

    private DormWebServiceRequest simpleJar;
    private DormWebServiceRequest simpleJarSha1;
    private DormWebServiceRequest simpleJarMd5;
    private DormWebServiceRequest simplePom;
    private DormWebServiceRequest simplePomSha1;
    private DormWebServiceRequest simplePomMd5;

    public MavenWebServiceRequestFixtures() {
        this(new MavenPathFixtures());
    }

    public MavenWebServiceRequestFixtures(MavenPathFixtures pathFixtures) {
        this.pathFixtures = pathFixtures;

        getFiles();

        simpleJar = new DormWebServiceRequest.Builder()
                .origin(MavenMetadata.EXTENSION_NAME)
                .property("uri", pathFixtures.getJar())
                .file(jar)
                .build();

        simpleJarSha1 = new DormWebServiceRequest.Builder()
                .origin(MavenMetadata.EXTENSION_NAME)
                .property("uri", pathFixtures.getJarSha1Hash())
                .file(jarSha1)
                .build();

        simpleJarMd5 = new DormWebServiceRequest.Builder()
                .origin(MavenMetadata.EXTENSION_NAME)
                .property("uri", pathFixtures.getJarMd5Hash())
                .file(jarMd5)
                .build();

        simplePom = new DormWebServiceRequest.Builder()
                .origin(MavenMetadata.EXTENSION_NAME)
                .property("uri", pathFixtures.getPom())
                .file(pom)
                .build();

        simplePomSha1 = new DormWebServiceRequest.Builder()
                .origin(MavenMetadata.EXTENSION_NAME)
                .property("uri", pathFixtures.getPomSha1Hash())
                .file(pomSha1)
                .build();

        simplePomMd5 = new DormWebServiceRequest.Builder()
                .origin(MavenMetadata.EXTENSION_NAME)
                .property("uri", pathFixtures.getPomMd5Hash())
                .file(pomMd5)
                .build();
    }

    private void getFiles() {

        try {
            jar = new File(getClass().getResource(BASE_PATH + ".jar").toURI());
            jarSha1 = new File(getClass().getResource(BASE_PATH + ".jar.sha1").toURI());
            jarMd5 = new File(getClass().getResource(BASE_PATH + ".jar.md5").toURI());
            pom = new File(getClass().getResource(BASE_PATH + ".pom").toURI());
            pomSha1 = new File(getClass().getResource(BASE_PATH + ".pom.sha1").toURI());
            pomMd5 = new File(getClass().getResource(BASE_PATH + ".pom.md5").toURI());
        } catch (URISyntaxException e) {
            
        }

        if (!DormFileUtils.allExists(jar, jarSha1, jarMd5, pom, pomSha1, pomMd5)) {
            throw new MavenException("One or more sample files are null");
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

    public File getJar() {
        return jar;
    }

    public File getJarSha1() {
        return jarSha1;
    }

    public File getJarMd5() {
        return jarMd5;
    }

    public File getPom() {
        return pom;
    }

    public File getPomSha1() {
        return pomSha1;
    }

    public File getPomMd5() {
        return pomMd5;
    }
}
