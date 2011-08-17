package com.zenika.dorm.maven.model.impl;

import java.util.Arrays;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class MavenFileType {

    public static final String JAR = "jar";
    public static final String POM = "pom";
    public static final String SHA1 = "sha1";
    public static final String MD5 = "md5";

    public static final String[] EXTENSIONS = {JAR, POM, SHA1, MD5};

    public static boolean isMavenType(String type) {

        if (null == type) {
            return false;
        }

        if (Arrays.asList(EXTENSIONS).contains(type)) {
            return true;
        }

        return false;
    }
}
