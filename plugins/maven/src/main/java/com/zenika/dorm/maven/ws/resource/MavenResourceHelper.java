package com.zenika.dorm.maven.ws.resource;

import com.zenika.dorm.maven.exception.MavenResourceException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenResourceHelper {

    public static String formatGroupId(String groupId) {

        if (StringUtils.isBlank(groupId)) {
            throw new MavenResourceException("Groupd id is required");
        }

        return groupId.replace('/', '.');
    }
}
