package com.zenika.dorm.maven.model.impl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class MavenFileType {

    public static final String JAR = "jar";
    public static final String POM = "pom";
    public static final String SHA1 = "sha1";

    public static boolean isMavenType(String type) {

        if (null == type) {
            return false;
        }

        type = type.toLowerCase();

        if (type == JAR || type == POM || type == SHA1) {
            return true;
        }

        return false;
    }
}
