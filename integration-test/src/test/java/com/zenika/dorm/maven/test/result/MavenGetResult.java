package com.zenika.dorm.maven.test.result;

import com.zenika.dorm.maven.test.model.MavenGetResource;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.File;
import java.io.InputStream;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenGetResult {

    private InputStream content;
    private int httpCode;
    private String contentType;
    private long length;

    public MavenGetResult(){

    }

    public MavenGetResult(InputStream content, int httpCode, String contentType, long length) {
        this.content = content;
        this.httpCode = httpCode;
        this.contentType = contentType;
        this.length = length;
    }

    public InputStream getContent() {
        return content;
    }

    @JsonIgnore
    public void setContent(InputStream content) {
        this.content = content;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getContentType() {
        return contentType;
    }

    @JsonIgnore
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getLength() {
        return length;
    }

    @JsonIgnore
    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "MavenGetResult{" +
                "httpCode=" + httpCode +
                ", contentType='" + contentType + '\'' +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MavenGetResult)) return false;

        MavenGetResult result = (MavenGetResult) o;

        if (httpCode != result.httpCode) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = httpCode;
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        return result;
    }
}
