package com.zenika.dorm.maven.model;

import com.zenika.dorm.core.util.DormStringUtils;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenConstant;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFilename {

    private static final String REGEX_TIMESTAMP = "^[0-9]*(\\.)[0-9]*$";

    private String fullFilename;
    private String filename;
    private String extension;
    private String classifier;
    private String packaging;
    private String timestamp;
    private String buildNumber;

    private MavenUri uri;

    public MavenFilename(MavenUri uri) {
        this.uri = uri;
        this.fullFilename = uri.getFilename();
        extractFields();
    }

    private void extractFields() {

        String filenameBeginning = uri.getArtifactId() + "-" + uri.getVersion() + "-";

        if (!fullFilename.startsWith(filenameBeginning)) {
            throw new MavenException("Maven filename must start with : " + filenameBeginning);
        }

        // remove artifactid and version from the current filename
        String filenameInWork = fullFilename.substring(fullFilename.indexOf(filenameBeginning +
                filenameBeginning.length()));

        if (uri.isSnapshot()) {

            String[] elements = filenameInWork.split("-");

            if (elements.length < 2 || DormStringUtils.oneIsBlank(elements[0], elements[1])) {
                throw new MavenException("Maven snaphost filename must contain at least a timestamp and buildnumber");
            }

            timestamp = elements[0];
            if (!timestamp.matches(REGEX_TIMESTAMP)) {
                throw new MavenException("Maven snapshot timestamp is invalid : " + timestamp);
            }

            buildNumber = elements[1];
            if (!StringUtils.isNumeric(buildNumber)) {
                throw new MavenException("Maven snapshot buildnumber is invalid : " + buildNumber);
            }

            // remove timestamp and buildnumber occurences from the current filename
            String snapshotElements = timestamp + "-" + buildNumber;
            filenameInWork = filenameInWork.substring(filenameInWork.indexOf(snapshotElements +
                    snapshotElements.length()));
        }

        if (filenameInWork.startsWith("-")) {
            classifier = filenameInWork.substring(1, filenameInWork.indexOf(".") - 1);
        }

        

        extension = FilenameUtils.getExtension(filenameInWork);

        if (StringUtils.isBlank(extension)) {
            throw new MavenException("Maven extension is required in : " + filenameInWork);
        }

        // extension is hash file
        if (DormStringUtils.equlasOne(extension, MavenConstant.FileExtension.SHA1,
                MavenConstant.FileExtension.MD5)) {

            // get previous extension
            packaging = FilenameUtils.getExtension(FilenameUtils.removeExtension(filenameInWork));
        }

        // packaging is extension
        else {
            packaging = extension;
        }
    }

    public String getFullFilename() {
        return fullFilename;
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

    public String getPackaging() {
        return packaging;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBuildNumber() {
        return buildNumber;
    }
}
