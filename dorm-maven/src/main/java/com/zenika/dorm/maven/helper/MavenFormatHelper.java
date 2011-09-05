package com.zenika.dorm.maven.helper;

import com.zenika.dorm.core.util.DormStringUtils;
import com.zenika.dorm.maven.exception.MavenException;
import org.apache.commons.io.FilenameUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MavenFormatHelper {

    private MavenFormatHelper() {
    }

    public static String formatUrl(String path, String filename) {

        if (DormStringUtils.oneIsBlank(path, filename)) {
            throw new MavenException("Maven path and filename is required");
        }

        return FilenameUtils.normalizeNoEndSeparator(path + "/" + filename);
    }
}
