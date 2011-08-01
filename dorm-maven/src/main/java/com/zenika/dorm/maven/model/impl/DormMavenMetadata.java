//package com.zenika.dorm.maven.model.impl;
//
//import com.zenika.dorm.core.model.old.MetadataExtension;
//
//public class DormMavenMetadata extends MetadataExtension {
//
//    public final static String ORIGIN = "Maven";
//
//    protected String groupId;
//    protected String artifactId;
//    protected String version;
//    protected String extension;
//
//    public DormMavenMetadata() {
//
//    }
//
//    public DormMavenMetadata(String groupId, String artifactId, String version) {
//        this.groupId = groupId;
//        this.artifactId = artifactId;
//        this.version = version;
//    }
//
//    public DormMavenMetadata(String groupId, String artifactId, String version, String extension) {
//        this(groupId, artifactId, version);
//        this.extension = extension;
//    }
//
//    public String getArtifactName() {
//        return groupId + ":" + artifactId + ":" + version;
//    }
//
//    public String getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(String groupId) {
//        this.groupId = groupId;
//    }
//
//    public String getArtifactId() {
//        return artifactId;
//    }
//
//    public void setArtifactId(String artifactId) {
//        this.artifactId = artifactId;
//    }
//
//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//    public String getExtension() {
//        return extension;
//    }
//
//    public void setExtension(String extension) {
//        this.extension = extension;
//    }
//}
