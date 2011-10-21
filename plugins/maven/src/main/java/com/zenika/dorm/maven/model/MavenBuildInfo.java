package com.zenika.dorm.maven.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenBuildInfo {

    public static final String METADATA_CLASSIFIER = "classifier";
    public static final String METADATA_TIMESTAMP = "timestamp";
    public static final String METADATA_EXTENSION = "extension";
    public static final String METADATA_BUILDNUMBER = "buildNumber";

    private String classifier;
    private String timestamp;
    private String buildNumber;
    private String extension;

    public MavenBuildInfo(String extension, String classifier, String timestamp, String buildNumber) {
        this.extension = StringUtils.defaultIfBlank(extension, "");
        this.classifier = StringUtils.defaultIfBlank(classifier, "");
        this.timestamp = StringUtils.defaultIfBlank(timestamp, "");
        this.buildNumber = StringUtils.defaultIfBlank(buildNumber, "");
    }

    public MavenBuildInfo() {

    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
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
                .append("classifier", classifier)
                .append("timestamp", timestamp)
                .append("buildNumber", buildNumber)
                .append("extension", extension)
                .appendSuper(super.toString())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MavenBuildInfo that = (MavenBuildInfo) o;

        if (buildNumber != null ? !buildNumber.equals(that.buildNumber) : that.buildNumber != null) return false;
        if (classifier != null ? !classifier.equals(that.classifier) : that.classifier != null) return false;
        if (extension != null ? !extension.equals(that.extension) : that.extension != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classifier != null ? classifier.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (buildNumber != null ? buildNumber.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        return result;
    }
}
