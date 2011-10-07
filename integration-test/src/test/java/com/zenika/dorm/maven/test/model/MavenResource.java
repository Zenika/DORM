package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.test.result.MavenGetResult;
import com.zenika.dorm.maven.test.result.MavenPutResult;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@JsonSubTypes({
        @JsonSubTypes.Type(value = MavenGetResource.class, name = "getResource"),
        @JsonSubTypes.Type(value = MavenPutResource.class, name = "putResource")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class MavenResource {
    private String pomUrl;
    private String pomSha1Url;
    private String pomMd5Url;
    private String jarUrl;
    private String jarSha1Url;
    private String jarMd5Url;

    private String baseUri;

    public String getPomUrl() {
        return pomUrl;
    }

    public String getPomSha1Url() {
        return pomSha1Url;
    }

    public String getPomMd5Url() {
        return pomMd5Url;
    }

    public String getJarUrl() {
        return jarUrl;
    }

    public String getJarSha1Url() {
        return jarSha1Url;
    }

    public String getJarMd5Url() {
        return jarMd5Url;
    }

    @JsonIgnore
    public String getPomUri() {
        return toUri(getPomUrl());
    }

    @JsonIgnore
    public String getPomSha1Uri() {
        return toUri(getPomSha1Url());
    }

    @JsonIgnore
    public String getPomMd5Uri() {
        return toUri(getPomMd5Url());
    }

    @JsonIgnore
    public String getJarUri() {
        return toUri(getJarUrl());
    }

    @JsonIgnore
    public String getJarSha1Uri() {
        return toUri(getJarSha1Url());
    }

    @JsonIgnore
    public String getJarMd5Uri() {
        return toUri(getJarMd5Url());
    }

    public void setPomUrl(String pomUrl) {
        this.pomUrl = pomUrl;
    }

    public void setPomSha1Url(String pomSha1Url) {
        this.pomSha1Url = pomSha1Url;
    }

    public void setPomMd5Url(String pomMd5Url) {
        this.pomMd5Url = pomMd5Url;
    }

    public void setJarUrl(String jarUrl) {
        this.jarUrl = jarUrl;
    }

    public void setJarSha1Url(String jarSha1Url) {
        this.jarSha1Url = jarSha1Url;
    }

    public void setJarMd5Url(String jarMd5Url) {
        this.jarMd5Url = jarMd5Url;
    }

    private String toUri(String url) {
        StringBuilder builder = new StringBuilder(50);
        builder.append(baseUri);
        if (!(baseUri.endsWith("/") && url.startsWith("/"))) {
            builder.append("/");
        }
        builder.append(url);
        return builder.toString();
    }

    @JsonIgnore
    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MavenResource)) return false;

        MavenResource that = (MavenResource) o;

        if (baseUri != null ? !baseUri.equals(that.baseUri) : that.baseUri != null) return false;
        if (jarMd5Url != null ? !jarMd5Url.equals(that.jarMd5Url) : that.jarMd5Url != null) return false;
        if (jarSha1Url != null ? !jarSha1Url.equals(that.jarSha1Url) : that.jarSha1Url != null) return false;
        if (jarUrl != null ? !jarUrl.equals(that.jarUrl) : that.jarUrl != null) return false;
        if (pomMd5Url != null ? !pomMd5Url.equals(that.pomMd5Url) : that.pomMd5Url != null) return false;
        if (pomSha1Url != null ? !pomSha1Url.equals(that.pomSha1Url) : that.pomSha1Url != null) return false;
        if (pomUrl != null ? !pomUrl.equals(that.pomUrl) : that.pomUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pomUrl != null ? pomUrl.hashCode() : 0;
        result = 31 * result + (pomSha1Url != null ? pomSha1Url.hashCode() : 0);
        result = 31 * result + (pomMd5Url != null ? pomMd5Url.hashCode() : 0);
        result = 31 * result + (jarUrl != null ? jarUrl.hashCode() : 0);
        result = 31 * result + (jarSha1Url != null ? jarSha1Url.hashCode() : 0);
        result = 31 * result + (jarMd5Url != null ? jarMd5Url.hashCode() : 0);
        result = 31 * result + (baseUri != null ? baseUri.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MavenResource");
        sb.append("{baseUri='").append(baseUri).append('\'');
        sb.append(", jarMd5Uri='").append(getJarMd5Uri()).append('\'');
        sb.append(", jarMd5Url='").append(jarMd5Url).append('\'');
        sb.append(", jarSha1Uri='").append(getJarSha1Uri()).append('\'');
        sb.append(", jarSha1Url='").append(jarSha1Url).append('\'');
        sb.append(", jarUri='").append(getJarUri()).append('\'');
        sb.append(", jarUrl='").append(jarUrl).append('\'');
        sb.append(", pomMd5Uri='").append(getPomMd5Uri()).append('\'');
        sb.append(", pomMd5Url='").append(pomMd5Url).append('\'');
        sb.append(", pomSha1Uri='").append(getPomSha1Uri()).append('\'');
        sb.append(", pomSha1Url='").append(pomSha1Url).append('\'');
        sb.append(", pomUri='").append(getPomUri()).append('\'');
        sb.append(", pomUrl='").append(pomUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
