package com.zenika.dorm.core.model;

import org.apache.commons.lang3.StringUtils;

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

    public String getLocation() {
        return location;
    }

    public String getHashMd5() {
        return hashMd5;
    }

    public String getHashSha1() {
        return hashSha1;
    }

    public boolean hasHashMd5() {
        return StringUtils.isNotEmpty(hashMd5);
    }

    public boolean hasHashSha1() {
        return StringUtils.isNotEmpty(hashSha1);
    }

    public boolean hasLocation() {
        return StringUtils.isNotEmpty(location);
    }
}
