package com.zenika.dorm.maven.helper;

import com.zenika.dorm.maven.model.impl.MavenConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MavenSpecificHelper {

    private MavenSpecificHelper() {
    }

    public static boolean isMavenMetadataFile(String filename) {
        return StringUtils.equals(filename, MavenConstant.Other.MAVEN_METADATA_XML);
    }

    public static boolean isSnapshot(String version) {
        return StringUtils.contains(version, MavenConstant.Other.SNAPSHOT);
    }
}