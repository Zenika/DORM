package com.zenika.dorm.maven.test.grinder;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class GrinderStatistics {

    private Long errors;
    private Long timedTests;
    private Long responseStatus;
    private Long responseLength;
    private Long responseErrors;
    private Long connectTime;
    private Long firstByteTime;

    public String[] toArray(){
        return new String[] {
                timedTests.toString(), errors.toString(), responseStatus.toString(), responseLength.toString(),
                responseErrors.toString(), connectTime.toString(), firstByteTime.toString()
        };
    }

    public void setErrors(Long errors) {
        this.errors = errors;
    }

    public void setTimedTests(Long timedTests) {
        this.timedTests = timedTests;
    }

    public void setResponseStatus(Long responseStatus) {
        this.responseStatus = responseStatus;
    }

    public void setResponseLength(Long responseLength) {
        this.responseLength = responseLength;
    }

    public void setResponseErrors(Long responseErrors) {
        this.responseErrors = responseErrors;
    }

    public void setConnectTime(Long connectTime) {
        this.connectTime = connectTime;
    }

    public void setFirstByteTime(Long firstByteTime) {
        this.firstByteTime = firstByteTime;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GrinderStatistics");
        sb.append("{connectTime=").append(connectTime);
        sb.append(", errors=").append(errors);
        sb.append(", firstByteTime=").append(firstByteTime);
        sb.append(", responseErrors=").append(responseErrors);
        sb.append(", responseLength=").append(responseLength);
        sb.append(", responseStatus=").append(responseStatus);
        sb.append(", timedTests=").append(timedTests);
        sb.append('}');
        return sb.toString();
    }
}
