package com.zenika.dorm.maven.test.fixtures;

import com.zenika.dorm.core.util.DormFileUtils;
import com.zenika.dorm.maven.exception.MavenException;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFileFixtures {

    private static final String BASE_PATH = "/samples/commons-io/commons-io-2.0.1";
    private File jar;
    private File jarSha1;
    private File jarMd5;
    private File pom;
    private File pomSha1;
    private File pomMd5;

    public MavenFileFixtures() {

        getFiles();
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
