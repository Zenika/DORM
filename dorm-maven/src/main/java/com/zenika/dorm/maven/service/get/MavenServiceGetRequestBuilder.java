package com.zenika.dorm.maven.service.get;

import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.impl.get.DefaultDormServiceGetRequestBuilder;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenServiceGetRequestBuilder extends DefaultDormServiceGetRequestBuilder {

    private MavenMetadataExtension metadata;

    public MavenServiceGetRequestBuilder(String processName, MavenMetadataExtension metadata) {
        super(processName, metadata);
        this.metadata = metadata;
    }

    public MavenServiceGetRequestBuilder withGroupId() {
        request.addExtensionWhereClause(MavenMetadataExtension.METADATA_GROUPID, metadata.getGroupId());
        return this;
    }

    public MavenServiceGetRequestBuilder withArtifactId() {
        request.addExtensionWhereClause(MavenMetadataExtension.METADATA_ARTIFACTID, metadata.getArtifactId());
        return this;
    }

    public MavenServiceGetRequestBuilder withVersion() {
        request.addExtensionWhereClause(MavenMetadataExtension.METADATA_VERSION, metadata.getVersion());
        return this;
    }

    public MavenServiceGetRequestBuilder withClassifier() {
        if (StringUtils.isNotBlank(metadata.getClassifier())) {
            request.addExtensionWhereClause(MavenMetadataExtension.METADATA_SNAPSHOT, metadata.getClassifier());
        }

        return this;
    }

    public MavenServiceGetRequestBuilder withPackaging() {
        if (StringUtils.isNotBlank(metadata.getPackaging())) {
            request.addExtensionWhereClause(MavenMetadataExtension.METADATA_SNAPSHOT, metadata.getPackaging());
        }
        return this;
    }

    public MavenServiceGetRequestBuilder withTimestamp() {
        if (StringUtils.isNotBlank(metadata.getTimestamp())) {
            request.addExtensionWhereClause(MavenMetadataExtension.METADATA_SNAPSHOT, metadata.getTimestamp());
        }
        return this;
    }

    public MavenServiceGetRequestBuilder withExtension() {
        if (StringUtils.isNotBlank(metadata.getExtension())) {
            request.addExtensionWhereClause(MavenMetadataExtension.METADATA_SNAPSHOT, metadata.getExtension());
        }
        return this;
    }

    public MavenServiceGetRequestBuilder withBuildNumber() {
        if (StringUtils.isNotBlank(metadata.getBuildNumber())) {
            request.addExtensionWhereClause(MavenMetadataExtension.METADATA_SNAPSHOT, metadata.getBuildNumber());
        }
        return this;
    }

    public MavenServiceGetRequestBuilder withSnapshot() {
        request.addExtensionWhereClause(MavenMetadataExtension.METADATA_SNAPSHOT,
                Boolean.toString(metadata.isSnapshot()));
        return this;
    }

    public MavenServiceGetRequestBuilder withAll() {
        withGroupId();
        withArtifactId();
        withVersion();
        withExtension();
        withPackaging();
        withClassifier();
        withTimestamp();
        withBuildNumber();
        withSnapshot();
        return this;
    }
}
