package com.zenika.dorm.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DormFileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DormFileUtils.class);

    private DormFileUtils() {
    }

    public static boolean allExists(File... files) {
        for (File file : files) {
            if (null == file || !file.exists()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("File is null : " + StringUtils.defaultIfBlank(file.getAbsolutePath(), "null"));
                }
                
                return false;
            }
        }

        return true;
    }
}
