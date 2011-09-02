//package com.zenika.dorm.maven.service.get;
//
//import com.zenika.dorm.core.service.impl.get.DefaultDormServiceGetRequestBuilder;
//import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
//import org.apache.commons.lang3.StringUtils;
//
///**
//* @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
//*/
//public class MavenServiceGetRequestBuilder extends DefaultDormServiceGetRequestBuilder {
//
//    private MavenMetadataExtension metadata;
//
//    public MavenServiceGetRequestBuilder(String processName, MavenMetadataExtension metadata) {
//        super(processName, metadata);
//        this.metadata = metadata;
//    }
//
//    public MavenServiceGetRequestBuilder withGroupId() {
//        request.getValues().addMetadataExtensionClause(MavenMetadataExtension.METADATA_GROUPID, metadata.getGroupId());
//        return this;
//    }
//
//    public MavenServiceGetRequestBuilder withArtifactId() {
//        request.getValues().addMetadataExtensionClause(MavenMetadataExtension.METADATA_ARTIFACTID, metadata.getArtifactId());
//        return this;
//    }
//
//    public MavenServiceGetRequestBuilder withVersion() {
//        request.getValues().addMetadataExtensionClause(MavenMetadataExtension.METADATA_VERSION, metadata.getVersion());
//        return this;
//    }
//
//    public MavenServiceGetRequestBuilder withClassifier() {
//        if (StringUtils.isNotBlank(metadata.getClassifier())) {
//            request.getValues().addMetadataExtensionClause(MavenMetadataExtension.METADATA_CLASSIFIER, metadata.getClassifier());
//        }
//
//        return this;
//    }
//
//    public MavenServiceGetRequestBuilder withPackaging() {
//        if (StringUtils.isNotBlank(metadata.getPackaging())) {
//            request.getValues().addMetadataExtensionClause(MavenMetadataExtension.METADATA_PACKAGING, metadata.getPackaging());
//        }
//        return this;
//    }
//
//    public MavenServiceGetRequestBuilder withTimestamp() {
//        if (StringUtils.isNotBlank(metadata.getTimestamp())) {
//            request.getValues().addMetadataExtensionClause(MavenMetadataExtension.METADATA_TIMESTAMP, metadata.getTimestamp());
//        }
//        return this;
//    }
//
//    public MavenServiceGetRequestBuilder withExtension() {
//        if (StringUtils.isNotBlank(metadata.getExtension())) {
//            request.getValues().addMetadataExtensionClause(MavenMetadataExtension.METADATA_EXTENSION, metadata.getExtension());
//        }
//        return this;
//    }
//
//    public MavenServiceGetRequestBuilder withBuildNumber() {
//        if (StringUtils.isNotBlank(metadata.getBuildNumber())) {
//            request.getValues().addMetadataExtensionClause(MavenMetadataExtension.METADATA_BUILDNUMBER, metadata.getBuildNumber());
//        }
//        return this;
//    }
//
//    public MavenServiceGetRequestBuilder withSnapshot() {
//        request.getValues().addMetadataExtensionClause(MavenMetadataExtension.METADATA_SNAPSHOT,
//                Boolean.toString(metadata.isSnapshot()));
//        return this;
//    }
//
//    public MavenServiceGetRequestBuilder withAll() {
//        withGroupId();
//        withArtifactId();
//        withVersion();
//        withExtension();
//        withPackaging();
//        withClassifier();
//        withTimestamp();
//        withBuildNumber();
//        withSnapshot();
//        return this;
//    }
//}
