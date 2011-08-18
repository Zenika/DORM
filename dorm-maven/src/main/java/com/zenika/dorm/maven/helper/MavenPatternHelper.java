package com.zenika.dorm.maven.helper;

import com.zenika.dorm.core.exception.CoreException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenPatternHelper {

    /**
     * Pattern whose compile the STRING_PATTERN_CLASSIFIER.
     */
    private static final Pattern PATTERN_CLASSIFIER;
     /**
     * Pattern whose compile the STRING_PATTERN_TIME_STAMP.
     */
    private static final Pattern PATTERN_TIME_STAMP;
    /**
     * String regex to match the classifier
     * <p><i>Example : </i></p>
     * <pre>
     * commons-io-1.4-<i>bin</i>.sha1
     * <pre>bin is the classifier</pre>
     * </pre>
     */
    private static final String STRING_PATTERN_CLASSIFIER = "\\-[0-9]+\\.[0-9]+\\-[0-9]\\.";
    private static final String STRING_PATTERN_TIME_STAMP = "\\-[a-z]+\\.";

    static {
        PATTERN_CLASSIFIER = Pattern.compile(STRING_PATTERN_CLASSIFIER);
        PATTERN_TIME_STAMP = Pattern.compile(STRING_PATTERN_TIME_STAMP);
    }

    /**
     * Test if the classifier exist in the file name.
     * <p><i>Example :</i></p>
     * <pre>
     * commons-io-1.4-<i>bin</i>.sha1
     * <pre>bin is the classifier</pre>
     * </pre>
     *
     * @param fileName the name of the file
     * @return true if the classifier exist
     */
    public static boolean isHasClassifier(String fileName) {
        return PATTERN_CLASSIFIER.matcher(fileName).find();
    }

    public static boolean isSnapshotVersion(String version) {
        return version.contains("SNAPSHOT");
    }

    /**
     * Return the artifact's classifier according to the file name. If the classifier doesn't exist an exception is throwing.
     * <p><i>Example : </i>
     * <pre>
     * commons-io-1.4-<i>bin</i>.sha1
     * return : bin
     * </pre>
     *
     * @param filename name of the file
     * @return the classifier
     */
    public static String getClassifier(String filename) {
        Matcher matcher = PATTERN_CLASSIFIER.matcher(filename);
        if (matcher.find()) {
            String classifier = matcher.group();
            return classifier.substring(1, classifier.length() - 1);
        } else {
            throw new CoreException("The filename must contains a classifier");
        }
    }

    // todo:Refactor when snapshot version is enabled
    public static String getSnapshotTimeStamp(String fileName) {
        return null;
    }

}
