package com.zenika.dorm.maven.test.result;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenPutResult {

    private int httpCode;

    public MavenPutResult(){

    }

    public MavenPutResult(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MavenPutResult)) return false;

        MavenPutResult result = (MavenPutResult) o;

        if (httpCode != result.httpCode) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return httpCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MavenPutResult");
        sb.append("{httpCode=").append(httpCode);
        sb.append('}');
        return sb.toString();
    }
}
