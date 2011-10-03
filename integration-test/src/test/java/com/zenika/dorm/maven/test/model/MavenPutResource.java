package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.test.result.MavenPutResult;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenPutResource extends MavenResource{

    private MavenPutResult expectedResult;

    private String pomPath;
    private String pomPathMd5;
    private String pomPathSha1;
    private String jarPath;
    private String jarPathMd5;
    private String jarPathSha1;

    public MavenPutResult getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(MavenPutResult expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getPomPath() {
        return pomPath;
    }

    public void setPomPath(String pomPath) {
        this.pomPath = pomPath;
    }

    public String getPomPathMd5() {
        return pomPathMd5;
    }

    public void setPomPathMd5(String pomPathMd5) {
        this.pomPathMd5 = pomPathMd5;
    }

    public String getPomPathSha1() {
        return pomPathSha1;
    }

    public void setPomPathSha1(String pomPathSha1) {
        this.pomPathSha1 = pomPathSha1;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getJarPathMd5() {
        return jarPathMd5;
    }

    public void setJarPathMd5(String jarPathMd5) {
        this.jarPathMd5 = jarPathMd5;
    }

    public String getJarPathSha1() {
        return jarPathSha1;
    }

    public void setJarPathSha1(String jarPathSha1) {
        this.jarPathSha1 = jarPathSha1;
    }
}
