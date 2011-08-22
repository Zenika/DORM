package com.zenika.dorm.maven.model.formatter;

import com.zenika.dorm.core.util.DormStringUtils;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFilenameFormatter {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFilenameFormatter.class);

    private static final String SEPARATOR = "-";
    private static final String REGEX = "(.+)(?:\\.)$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private String filename;
    private String artifactId;
    private String version;
    private String timestamp;
    private String buildNumber;
    private String classifier;
    private String extension;

    public MavenFilenameFormatter(String filename) {

        if (StringUtils.isBlank(filename)) {
            throw new MavenException("Filename to format is blank");
        }

        this.filename = filename;
    }

    public void format() {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Format maven filename : " + filename);
        }

        // do not format if it was done before
        if (null != artifactId) {
            return;
        }

        String[] split = filename.split(SEPARATOR);
        if (null == split || split.length < 3) { // minimum 3 elements : artifactid - version - timestamp
            LOG.error("Filename must be formatted with \"-\" between elements");
            return;
        }

        int current = split.length - 1;

        String last = split[current];
        if (null == last) {
            LOG.error("Cannot format filename");
            return;
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Last element with extension from filename : " + last);
        }

        String[] withExtension = last.split(".");
        if (null == withExtension || withExtension.length < 2 ||
                DormStringUtils.areBlanks(withExtension[0], withExtension[1])) {
            LOG.error("Cannot format extension from filename");
            return;
        }

        last = withExtension[0];
        extension = withExtension[1];

        if (StringUtils.isNumeric(last)) {
            buildNumber = last;
            classifier = "";
        } else {
            buildNumber = split[--current];
            classifier = last;
        }

        timestamp = split[--current];

        version = split[--current];
        if (StringUtils.equals(version, MavenConstant.Other.SNAPSHOT)) {
            version = split[--current] + "-" + version;
        }

        artifactId = split[0];
        for (int i = 1; i <= current; i++) {
            artifactId += "-" + split[i];
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
                '}';
    }
}
