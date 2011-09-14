package com.zenika.dorm.maven.model.formatter;

import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.exception.MavenFormatterException;
import com.zenika.dorm.maven.model.MavenConstant;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFilenameFormatter {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFilenameFormatter.class);

    private static final String SEPARATOR = "-";
    private static final String REGEX_TIMESTAMP = "^[0-9]*(\\.)[0-9]*$";
    private static final String REGEX_VERSION = "^(([0-9])(\\.)?)*[^\\.]$";

    private String filename;
    private String artifactId;
    private String version;
    private String timestamp;
    private String buildNumber;
    private String classifier;
    private String extension;

    private String[] elements;
    private int index;

    public MavenFilenameFormatter(String filename) {

        if (StringUtils.isBlank(filename)) {
            throw new MavenException("Filename to format is blank");
        }

        this.filename = filename;
    }

    /**
     * Format the filename from right to left
     */
    public void format() {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Format maven filename : " + filename);
        }

        // do not format if it was done before
        if (null != artifactId) {
            return;
        }

        if (StringUtils.isBlank(filename)) {
            throw new MavenFormatterException("Filename to format is required");
        }


        extension = FilenameUtils.getExtension(filename);
        if (StringUtils.isBlank(extension)) {
            throw new MavenFormatterException("Filename to format have no extension");
        }

        String filenameWithoutExtension = FilenameUtils.removeExtension(filename);

        // Fix: if filename is equal to "foo.jar.sha1" for example
        if (extension.equals(MavenConstant.FileExtension.SHA1) ||
                extension.equals(MavenConstant.FileExtension.MD5)) {
            filenameWithoutExtension = FilenameUtils.removeExtension(filenameWithoutExtension);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Filename extension : " + extension);
            LOG.debug("Filename without extension : " + filenameWithoutExtension);
        }

        elements = filenameWithoutExtension.split(SEPARATOR);

        // minimum 3 elements : artifactid - version - timestamp
        if (null == elements || elements.length < 3) {
            throw new MavenFormatterException("Filename must be formatted with \"-\" between elements");
        }

        index = elements.length;

        String last = getPreviousElement();

        // build number
        if (StringUtils.isNumeric(last)) {
            buildNumber = last;
            classifier = "";
        }

        // classifier
        else {
            buildNumber = getPreviousElement();
            classifier = last;

            if (!StringUtils.isNumeric(buildNumber)) {
                throw new MavenFormatterException("Buildnumber must be numeric : " + buildNumber);
            }
        }

        timestamp = getPreviousElement();
        validateTimestamp(timestamp);

        version = getPreviousElement();
        if (StringUtils.equals(version, MavenConstant.Special.SNAPSHOT)) {
            String versionWithoutSnapshot = getPreviousElement();
            validateVersion(versionWithoutSnapshot);
            version = versionWithoutSnapshot + "-" + version;
        } else {
            validateVersion(version);
        }

        artifactId = elements[0];
        if (index == 0 || StringUtils.isBlank(artifactId)) {
            throw new MavenFormatterException("Cannot format artifactId from filename");
        }

        for (int i = 1; i < index; i++) {
            artifactId += "-" + elements[i];
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Format complete with values : " + toString());
        }
    }

    private String getPreviousElement() {

        if (index == 0) {
            throw new MavenFormatterException("Cannot format the filename, something excepected before : " +
                    elements[index]);
        }

        String element = elements[--index];

        if (StringUtils.isBlank(element)) {
            throw new MavenFormatterException("Element is required before : " + elements[index]);
        }

        return element;
    }

    private void validateTimestamp(String timestamp) {

        if (StringUtils.isBlank(timestamp)) {
            throw new MavenFormatterException("Timestamp is required");
        }

        if (!timestamp.matches(REGEX_TIMESTAMP)) {
            throw new MavenFormatterException("Timestamp is invalid : " + timestamp);
        }
    }

    private void validateVersion(String version) {

        if (StringUtils.isBlank(version)) {
            throw new MavenFormatterException("Version is required");
        }

        if (!version.matches(REGEX_VERSION)) {
            throw new MavenFormatterException("Version is invalid : " + version);
        }
    }

    @Override
    public String toString() {
        return "MavenFilenameFormatter{" +
                "filename='" + filename + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", buildNumber='" + buildNumber + '\'' +
                ", classifier='" + classifier + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }

    public String getFilename() {
        return filename;
    }

    public String getArtifactId() {
        format();
        return artifactId;
    }

    public String getVersion() {
        format();
        return version;
    }

    public String getTimestamp() {
        format();
        return timestamp;
    }

    public String getBuildNumber() {
        format();
        return buildNumber;
    }

    public String getClassifier() {
        format();
        return classifier;
    }

    public String getExtension() {
        format();
        return extension;
    }
}