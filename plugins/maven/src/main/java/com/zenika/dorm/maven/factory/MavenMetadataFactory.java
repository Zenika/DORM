package com.zenika.dorm.maven.factory;

import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenBuildInfoBuilder;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.zenika.dorm.maven.model.MavenMetadata.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataFactory implements ExtensionMetadataFactory {

    @Override
    public String getExtensionName() {
        return EXTENSION_NAME;
    }

    @Override
    public DormMetadata createFromProperties(Map<String, String> properties) {
        MavenBuildInfo buildInfo = new MavenBuildInfoBuilder()
                .classifier(properties.get(METADATA_CLASSIFIER))
                .extension(properties.get(METADATA_EXTENSION))
                .timestamp(properties.get(METADATA_TIMESTAMP))
                .buildNumber(properties.get(METADATA_BUILDNUMBER))
                .build();

        return new MavenMetadataBuilder(properties.get(METADATA_ARTIFACTID))
                .groupId(properties.get(METADATA_GROUPID))
                .version(properties.get(METADATA_VERSION))
                .buildInfo(buildInfo)
                .build();
    }

    @Override
    public Map<String, String> toMap(DormMetadata metadata) {
        MavenMetadata mavenMetadata = (MavenMetadata) metadata;
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(METADATA_GROUPID, mavenMetadata.getGroupId());
        properties.put(METADATA_ARTIFACTID, mavenMetadata.getArtifactId());
        properties.put(METADATA_VERSION, mavenMetadata.getVersion());
        properties.put(METADATA_CLASSIFIER, mavenMetadata.getBuildInfo().getClassifier());
        properties.put(METADATA_EXTENSION, mavenMetadata.getBuildInfo().getExtension());
        properties.put(METADATA_SNAPSHOT, String.valueOf(mavenMetadata.isSnapshot()));
        properties.put(METADATA_TIMESTAMP, mavenMetadata.getBuildInfo().getTimestamp());
        properties.put(METADATA_BUILDNUMBER, mavenMetadata.getBuildInfo().getBuildNumber());
        return properties;
    }

}
