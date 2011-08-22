package com.zenika.dorm.maven.model.impl;

import java.util.Arrays;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class MavenConstant {

    public static abstract class Type {

        public static final String JAR = "jar";
        public static final String POM = "pom";
        public static final String SHA1 = "sha1";
        public static final String MD5 = "md5";

        public static final String[] EXTENSIONS = {JAR, POM, SHA1, MD5};

        public static boolean isMavenType(String type) {
            return Arrays.asList(EXTENSIONS).contains(type);
        }
    }

    public static abstract class Packaging {

        public static final String JAR = "jar";
        public static final String EAR = "ear";
        public static final String EJB = "ejb";
        public static final String RAR = "rar";
        public static final String WAR = "war";
        public static final String APP_CLIENT = "app-client";
        public static final String SHADE = "shade";

        public static final String[] EXTENSIONS = {JAR, EAR, EJB, RAR, WAR, APP_CLIENT, SHADE};

        public static boolean isPackagingType(String type) {
            return Arrays.asList(EXTENSIONS).contains(type);
        }
    }
}
