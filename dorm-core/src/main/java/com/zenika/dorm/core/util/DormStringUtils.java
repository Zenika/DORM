package com.zenika.dorm.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DormStringUtils {

    private DormStringUtils() {

    }

    public static boolean oneIsBlank(String... strings) {

        for (String string : strings) {
            if (StringUtils.isBlank(string)) {
                return true;
            }
        }

        return false;
    }
}
