package com.zenika.dorm.maven.model.impl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class MavenFileType {

    public static final String JAR = "jar";
    public static final String POM = "pom";
    public static final String SHA1 = "sha1";
    public static final String MD5 = "md5";

    public static boolean isMavenType(String type) {

        if (null == type) {
            return false;
        }

        if (type.equalsIgnoreCase(JAR) || type.equalsIgnoreCase(POM) || type.equalsIgnoreCase(SHA1) || type.equalsIgnoreCase(MD5)) {
            return true;
        }

        return false;
    }
}
