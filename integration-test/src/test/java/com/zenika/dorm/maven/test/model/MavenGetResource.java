package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.test.result.MavenGetResult;
import com.zenika.dorm.maven.test.result.MavenPutResult;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenGetResource extends MavenResource {

    private MavenGetResult expectedResult;

    public MavenGetResult getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(MavenGetResult expectedResult) {
        this.expectedResult = expectedResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MavenGetResource)) return false;
        if (!super.equals(o)) return false;

        MavenGetResource resource = (MavenGetResource) o;

        if (expectedResult != null ? !expectedResult.equals(resource.expectedResult) : resource.expectedResult != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (expectedResult != null ? expectedResult.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("MavenGetResource");
        sb.append("{expectedResult=").append(expectedResult);
        sb.append('}');
        return sb.toString();
    }
}