package com.zenika.dorm.core.util;

import com.zenika.dorm.core.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DormFormatter {

    private static final Logger LOG = LoggerFactory.getLogger(DormFormatter.class);

    private DormFormatter() {

    }

    public static String formatMetadataExtensionQualifier(String qualifier) {

        if (isEmpty(qualifier)) {
            throw new CoreException("Metadata extension qualifier is null or empty").type(CoreException.Type.NULL);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("format qualifier : " + qualifier);
        }

        // qualifier can contains any number of times :
        // - any letter case insensitive
        // - any number
        // - ":"
        // - whitespace
        if (!qualifier.matches("^[a-zA-Z0-9 \\s]+([\\:][a-zA-Z0-9 \\s]+)*$")) {
            throw new CoreException("Extension qualifier can contains only letters, numbers, " +
                    "whitespaces and \":\"");

            
        }

        qualifier = qualifier.replaceAll(":", "-")
                .replaceAll(" ", "-")
                .trim();

        if (LOG.isDebugEnabled()) {
            LOG.debug("formatted qualifier : " + qualifier);
        }

        return qualifier;
    }

    public static String formatMetadataVersion(String version) {

        if (isEmpty(version)) {
            throw new CoreException("Metadata version is null or empty").type(CoreException.Type.NULL);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("format version : " + version);
        }

        // version can contains any number of times :
        // - any letter case insensitive
        // - any number
        // - "-", "."
        if (!version.matches("/[a-zA-Z]|\\d|-|\\.*/")) {
            throw new CoreException("Version can contains only letters, numbers, \"-\" and \".\"");
        }

        return version;
    }

    public static String formatMetadataType(String type) {

        if (isEmpty(type)) {
            throw new CoreException("Type is null or empty").type(CoreException.Type.NULL);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("format type : " + type);
        }

        // type can contains any number of times :
        // - any letter case insensitive
        // - any number
        // - "-"
        if (!type.matches("/[a-zA-Z]|\\d|-*/")) {
            throw new CoreException("Version can contains only letters, numbers and \"-\"");
        }

        type = type.trim();

        if (LOG.isDebugEnabled()) {
            LOG.debug("formatted type : " + type);
        }

        return type;
    }

    public static String formatMetadataQualifier(String extensionName, String extensionQualifier,
                                                 String version, String type) {

        String qualifier = extensionName + ":" + extensionQualifier + ":" + version + ":" + type;

        if (LOG.isDebugEnabled()) {
            LOG.debug("formatted metadata qualifier : " + qualifier);
        }

        return qualifier;
    }

    private static boolean isEmpty(String string) {
        return null == string || string.isEmpty();
    }
}