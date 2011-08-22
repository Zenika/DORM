package com.zenika.dorm.maven.util;

import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenConstant;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MavenFormatter {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFormatter.class);

    private MavenFormatter() {

    }

    private static final String REGEX_CLASSIFIER = "\\-[0-9]+\\.[0-9]+\\-[0-9]\\.";
    private static final String REGEX_TIMESTAMP = "\\-[a-z]+\\.";
    private static final String REGEX_BUILDNUMBER = "-[1-9]$";

    private static final Pattern PATTERN_CLASSIFIER = Pattern.compile(REGEX_CLASSIFIER);
    private static final Pattern PATTERN_TIMESTAMP = Pattern.compile(REGEX_TIMESTAMP);
    private static final Pattern PATTERN_BUILDNUMBER = Pattern.compile(REGEX_BUILDNUMBER);

    /**
     * Return the artifact's classifier according to the file name. If the classifier doesn't exist an exception is throwing.
     * <p><i>Example : </i>
     * <pre>
     * commons-io-1.4-<i>bin</i>.sha1
     * return : bin
     * </pre>
     *
     * @param filename the filename which may contain the classifier
     * @return the classifier or null if not exists
     */
    public static String getClassifierIfExists(String filename) {

        Matcher matcher = PATTERN_CLASSIFIER.matcher(filename);

        if (!matcher.find()) {
            return null;
        }

        String classifier = matcher.group();
        return classifier.substring(1, classifier.length() - 1);
    }

    public static String formatGroupId(String groupId) {

        if (StringUtils.isBlank(groupId)) {
            return null;
        }

        return groupId.replace('/', '.');
    }

    public static String getExtension(String filename) {

        String type = FilenameUtils.getExtension(filename);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Type of the maven file = " + type);
        }

        if (!MavenConstant.FileExtension.isMavenExtension(type)) {
            throw new MavenException("Invalid maven type : " + type);
        }

        return type;
    }

    public static String getTimestamp(String filename) {
        return null;
    }

    public static String getBuildNumber(String filename) {

        Matcher matcher = PATTERN_BUILDNUMBER.matcher(filename);

        if (!matcher.find()) {
            return null;
        }

        String buildNumber = matcher.group();
        buildNumber = buildNumber.substring(1, buildNumber.length() - 1);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Build number is : " + buildNumber);
        }

        return buildNumber;
    }

    public static boolean isMavenMetadataFile(String filename) {
        return MavenConstant.Other.MAVEN_METADATA_XML.equals(filename);
    }

    public static boolean isSnapshot(String version) {

        if (StringUtils.isBlank(version)) {
            throw new MavenException("Version cannot be null");
        }

        return version.contains(MavenConstant.Other.SNAPSHOT);
    }
}
