package com.zenika.dorm.maven.factory;

import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;

import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataFactory implements ExtensionMetadataFactory {

    @Override
    public DormMetadata createFromProperties(Map<String, String> properties) {
        return new MavenMetadataBuilder(properties.get(MavenMetadata.METADATA_ARTIFACTID))
                .groupId(properties.get(MavenMetadata.METADATA_GROUPID))
                .version(properties.get(MavenMetadata.METADATA_VERSION))
                .classifier(properties.get(MavenMetadata.METADATA_CLASSIFIER))
                .extension(properties.get(MavenMetadata.METADATA_EXTENSION))
                .snapshot(Boolean.valueOf(properties.get(MavenMetadata.METADATA_SNAPSHOT)))
                .timestamp(properties.get(MavenMetadata.METADATA_TIMESTAMP))
                .buildNumber(properties.get(MavenMetadata.METADATA_BUILDNUMBER))
                .build();
    }
}
