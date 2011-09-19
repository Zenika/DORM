package com.zenika.dorm.maven.factory;

import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.model.MavenMetadata;
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
        return new MavenMetadataBuilder(properties.get(METADATA_ARTIFACTID))
                .groupId(properties.get(METADATA_GROUPID))
                .version(properties.get(METADATA_VERSION))
                .classifier(properties.get(METADATA_CLASSIFIER))
                .extension(properties.get(METADATA_EXTENSION))
                .snapshot(Boolean.valueOf(properties.get(METADATA_SNAPSHOT)))
                .timestamp(properties.get(METADATA_TIMESTAMP))
                .buildNumber(properties.get(METADATA_BUILDNUMBER))
                .build();
    }

    @Override
    public Map<String, String> toMap(DormMetadata metadata) {
        MavenMetadata mavenMetadata = (MavenMetadata) metadata;
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(METADATA_GROUPID, mavenMetadata.getGroupId());
        properties.put(METADATA_ARTIFACTID, mavenMetadata.getArtifactId());
        properties.put(METADATA_VERSION, mavenMetadata.getVersion());
        properties.put(METADATA_CLASSIFIER, mavenMetadata.getClassifier());
        properties.put(METADATA_EXTENSION, mavenMetadata.getExtension());
        properties.put(METADATA_SNAPSHOT, String.valueOf(mavenMetadata.isSnapshot()));
        properties.put(METADATA_TIMESTAMP, mavenMetadata.getTimestamp());
        properties.put(METADATA_BUILDNUMBER, mavenMetadata.getBuildNumber());
        return properties;
    }

}
