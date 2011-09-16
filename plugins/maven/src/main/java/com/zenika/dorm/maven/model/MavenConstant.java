package com.zenika.dorm.maven.model;

import java.util.Arrays;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class MavenConstant {

    public static final String JAR = "jar";
    public static final String POM = "pom";
    public static final String EAR = "ear";
    public static final String EJB = "ejb";
    public static final String WAR = "war";

    public static abstract class Extension extends MavenConstant {

        public static final String SHA1 = "sha1";
        public static final String MD5 = "md5";
        public static final String ZIP = "zip";

        public static final String[] EXTENSIONS = {JAR, POM, EAR, EJB, WAR, SHA1, MD5, ZIP};

        public static boolean isMavenExtension(String type) {
            return Arrays.asList(EXTENSIONS).contains(type);
        }
    }

    public static abstract class Packaging extends MavenConstant {

        public static final String RAR = "rar";
        public static final String PAR = "par";
        public static final String MAVEN_PLUGIN = "maven-plugin";

        public static final String[] PACKAGINGS = {POM, JAR, MAVEN_PLUGIN, EJB, WAR, EAR, RAR, PAR};

        public static boolean isPackagingType(String type) {
            return Arrays.asList(PACKAGINGS).contains(type);
        }
    }

    public static abstract class Special {
        public static final String MAVEN_METADATA_XML = "maven-metadata.xml";
        public static final String SNAPSHOT = "SNAPSHOT";
    }
}
