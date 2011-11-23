package com.zenika.dorm.maven.factory;

import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.util.DormStringUtils;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenBuildInfoBuilder;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import org.apache.commons.lang3.StringUtils;

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
    public MavenMetadata fromMap(Long id, Map<String, String> properties) {

        MavenMetadataBuilder builder = new MavenMetadataBuilder();
        applyToBuilder(builder, id, properties);

        return builder.build();
    }

    @Override
    public Map<String, String> toMap(DormMetadata metadata) {

        MavenMetadata mavenMetadata = (MavenMetadata) metadata;

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(METADATA_GROUPID, mavenMetadata.getGroupId());
        properties.put(METADATA_ARTIFACTID, mavenMetadata.getArtifactId());
        properties.put(METADATA_VERSION, mavenMetadata.getVersion());
        properties.put(METADATA_SNAPSHOT, String.valueOf(mavenMetadata.isSnapshot()));

        if (null != mavenMetadata.getBuildInfo()) {
            properties.put(MavenBuildInfo.METADATA_CLASSIFIER, mavenMetadata.getBuildInfo().getClassifier());
            properties.put(MavenBuildInfo.METADATA_EXTENSION, mavenMetadata.getBuildInfo().getExtension());
            properties.put(MavenBuildInfo.METADATA_TIMESTAMP, mavenMetadata.getBuildInfo().getTimestamp());
            properties.put(MavenBuildInfo.METADATA_BUILDNUMBER, mavenMetadata.getBuildInfo().getBuildNumber());
        }

        return properties;
    }

    private void applyToBuilder(MavenMetadataBuilder builder, Long id, Map<String, String> properties) {

        String classifier = properties.get(MavenBuildInfo.METADATA_CLASSIFIER);
        String extension = properties.get(MavenBuildInfo.METADATA_EXTENSION);
        String timestamp = properties.get(MavenBuildInfo.METADATA_TIMESTAMP);
        String buildnumber = properties.get(MavenBuildInfo.METADATA_BUILDNUMBER);

        if (!DormStringUtils.oneIsBlank(classifier, extension, timestamp, buildnumber)) {

            MavenBuildInfo buildInfo = new MavenBuildInfoBuilder()
                    .classifier(StringUtils.defaultIfBlank(properties.get(MavenBuildInfo.METADATA_CLASSIFIER), null))
                    .extension(StringUtils.defaultIfBlank(properties.get(MavenBuildInfo.METADATA_EXTENSION), null))
                    .timestamp(StringUtils.defaultIfBlank(properties.get(MavenBuildInfo.METADATA_TIMESTAMP), null))
                    .buildNumber(StringUtils.defaultIfBlank(properties.get(MavenBuildInfo.METADATA_BUILDNUMBER), null))
                    .build();

            builder.buildInfo(buildInfo);
        }

        builder.id(id)
                .artifactId(properties.get(METADATA_ARTIFACTID))
                .groupId(properties.get(METADATA_GROUPID))
                .version(properties.get(METADATA_VERSION))
                .build();
    }

}
