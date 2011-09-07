package com.zenika.dorm.core.util;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DormUtils {

    private DormUtils() {
    }

    public static boolean isNullIn(Object... objects) {
        for (Object object : objects) {
            if (null == object) {
                return true;
            }
        }

        return false;
    }
}
