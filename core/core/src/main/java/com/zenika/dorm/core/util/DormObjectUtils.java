package com.zenika.dorm.core.util;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DormObjectUtils {

    private DormObjectUtils() {
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
