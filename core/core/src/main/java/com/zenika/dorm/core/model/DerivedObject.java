package com.zenika.dorm.core.model;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DerivedObject {

    
    protected String location;
    protected String hashMd5;
    protected String hashSha1;

    public void setHashMd5(String hashMd5) {
        this.hashMd5 = hashMd5;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHashSha1(String hashSha1) {
        this.hashSha1 = hashSha1;
    }
}
