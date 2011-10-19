package com.zenika.dorm.maven.model;

import com.zenika.dorm.core.util.DormStringUtils;
import com.zenika.dorm.maven.constant.MavenConstant;
import com.zenika.dorm.maven.exception.MavenException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenFilename {

    private static final String REGEX_TIMESTAMP = "^[0-9]*(\\.)[0-9]*$";
    private static final Pattern PATTERN_BUILDNUMBER = Pattern.compile("^(\\d+)\\D.*$");

    private String filename;
    private String extension;
    private String classifier;
    private String timestamp;
    private String buildNumber;

    private MavenUri uri;

    public MavenFilename(MavenUri uri, String filename) {
        this.uri = uri;
        this.filename = filename;
        extractFields();
    }

    public MavenFilename(MavenUri uri, MavenMetadata metadata) {
        MavenBuildInfo info = metadata.getBuildInfo();
        this.extension = info.getExtension();
        this.classifier = info.getClassifier();
        this.timestamp = info.getTimestamp();
        this.buildNumber = info.getBuildNumber();
        this.uri = uri;
        buildFileName();
    }

    private void buildFileName() {
        StringBuilder builder = new StringBuilder()
                .append(uri.getArtifactId())
                .append("-")
                .append(uri.getVersion());

        if ((classifier != null) && (!classifier.isEmpty())) {
            builder.append("-").append(classifier);
        }
        builder.append(".").append(extension);
        filename = builder.toString();
    }

    private void extractFields() {

        if (StringUtils.equals(filename, MavenConstant.Special.MAVEN_METADATA_XML)) {
            extension = "xml";
            return;
        }

        String filenameBeginning = uri.getArtifactId() + "-" + uri.getVersionWithtoutSnapshot();

        if (!filename.startsWith(filenameBeginning)) {
            throw new MavenException("Maven filename must start with : " + filenameBeginning);
        }

        // remove artifactid and version from the current filename
        String filenameInWork = filename.substring(filename.indexOf(filenameBeginning)
                + filenameBeginning.length());

        // is snapshot
        if (uri.isSnapshot()) {

            String[] elements = filenameInWork.substring(1).split("-", 2);

            if (elements.length < 2 || DormStringUtils.oneIsBlank(elements[0], elements[1])) {
                throw new MavenException("Maven snaphost filename must contain at least a timestamp and buildnumber");
            }

            timestamp = elements[0];
            if (!timestamp.matches(REGEX_TIMESTAMP)) {
                throw new MavenException("Maven snapshot timestamp is invalid : " + timestamp);
            }

            Matcher matcher = PATTERN_BUILDNUMBER.matcher(elements[1]);

            if (!matcher.find()) {
                throw new MavenException("Maven snapshot buildnumber is required in : " + filename);
            }

            buildNumber = matcher.group(1);

            if (!StringUtils.isNumeric(buildNumber)) {
                throw new MavenException("Maven snapshot buildnumber is invalid : " + buildNumber);
            }

            // remove timestamp and buildnumber occurences from the current filename
            String snapshotElements = "-" + timestamp + "-" + buildNumber;
            filenameInWork = filenameInWork.substring(filenameInWork.indexOf(snapshotElements) +
                    snapshotElements.length());
        }

        // get extension
        extension = FilenameUtils.getExtension(filenameInWork);

        if (StringUtils.isBlank(extension)) {
            throw new MavenException("Maven extension is required in : " + filenameInWork);
        }

        filenameInWork = FilenameUtils.removeExtension(filenameInWork);

        // is classifier
        if (filenameInWork.startsWith("-")) {

            // extension is hash file
            if (DormStringUtils.equlasOne(extension, MavenConstant.Extension.SHA1,
                    MavenConstant.Extension.MD5)) {

                extension = FilenameUtils.getExtension(filenameInWork) + "." + extension;
                filenameInWork = FilenameUtils.removeExtension(filenameInWork);
            }

            classifier = filenameInWork.substring(1);
        }

        // no classifier
        else {
            String extensionElement;
            while (StringUtils.isNotBlank(extensionElement = FilenameUtils.getExtension(filenameInWork))) {
                extension = extensionElement + "." + extension;
                filenameInWork = filenameInWork.substring(0,
                        filenameInWork.length() - (extensionElement.length() + 1));
            }
        }
    }

    public boolean isHashMd5(){
        return StringUtils.equals(extension, "md5");
    }

    public boolean isHashSha1(){
        return StringUtils.equals(extension, "sha1");
    }

    public boolean isPomFile(){
        return StringUtils.equals(extension, "pom") || StringUtils.endsWith(filename, ".pom");
    }

    public boolean isJarFile(){
        return StringUtils.equals(extension, "jar") || StringUtils.endsWith(filename, ".jar");
    }

    public String getFileNameWithoutExtension() {
        return FilenameUtils.getBaseName(filename);
    }

    public String getFilename() {
        return filename;
    }

    public String getExtension() {
        return extension;
    }

    public String getClassifier() {
        return classifier;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("filename", filename)
                .append("extension", extension)
                .append("classifier", classifier)
                .append("timestamp", timestamp)
                .append("buildNumber", buildNumber)
                .append("uri", uri)
                .appendSuper(super.toString())
                .toString();
    }
}
