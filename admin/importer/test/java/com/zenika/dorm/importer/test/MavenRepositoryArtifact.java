package com.zenika.dorm.importer.test;

import java.io.File;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenRepositoryArtifact {

    private File jarFile;
    private File jarMd5File;
    private File jarSha1File;
    private File pomFile;
    private File pomMd5File;
    private File pomSha1File;

    public File getJarFile() {
        return jarFile;
    }

    public void setJarFile(File jarFile) {
        this.jarFile = jarFile;
    }

    public File getJarMd5File() {
        return jarMd5File;
    }

    public void setJarMd5File(File jarMd5File) {
        this.jarMd5File = jarMd5File;
    }

    public File getJarSha1File() {
        return jarSha1File;
    }

    public void setJarSha1File(File jarSha1File) {
        this.jarSha1File = jarSha1File;
    }

    public File getPomFile() {
        return pomFile;
    }

    public void setPomFile(File pomFile) {
        this.pomFile = pomFile;
    }

    public File getPomMd5File() {
        return pomMd5File;
    }

    public void setPomMd5File(File pomMd5File) {
        this.pomMd5File = pomMd5File;
    }

    public File getPomSha1File() {
        return pomSha1File;
    }

    public void setPomSha1File(File pomSha1File) {
        this.pomSha1File = pomSha1File;
    }
}
