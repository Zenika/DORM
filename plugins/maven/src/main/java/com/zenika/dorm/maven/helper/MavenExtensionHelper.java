package com.zenika.dorm.maven.helper;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MavenExtensionHelper {

    public static boolean isHash(String extension) {

        if (StringUtils.isBlank(extension)) {
            return false;
        }

        // extension is composed of many (ex. jar.md5)
        if (extension.contains(".")) {
            extension = FilenameUtils.getExtension(extension);
        }

        return extension.equals("md5") || extension.equals("sha1");
    }
}
