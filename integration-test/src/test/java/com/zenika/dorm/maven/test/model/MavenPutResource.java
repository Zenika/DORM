package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.test.result.MavenPutResult;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenPutResource extends MavenResource{

    private MavenPutResult expectedResult;

    private String filePath;

    public MavenPutResult getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(MavenPutResult expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
