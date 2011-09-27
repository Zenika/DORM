package com.zenika.dorm.maven.model.builder;

import com.zenika.dorm.maven.model.MavenFilename;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.MavenUri;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataUriBuilder {

    public static MavenMetadata buildMavenMetadata(MavenUri uri) {

        MavenFilename filename = uri.getFilename();

        MavenBuildInfoBuilder buildInfoBuilder = new MavenBuildInfoBuilder()
                .extension(filename.getExtension());

        if (StringUtils.isNotBlank(filename.getClassifier())) {
            buildInfoBuilder.classifier(filename.getClassifier());
        }

        if (StringUtils.isNotBlank(filename.getTimestamp()) && StringUtils.isNotBlank(filename
                .getBuildNumber())) {
            buildInfoBuilder.timestamp(filename.getTimestamp());
            buildInfoBuilder.buildNumber(filename.getBuildNumber());
        }

        MavenMetadataBuilder builder = new MavenMetadataBuilder(uri.getArtifactId())
                .groupId(uri.getGroupId())
                .version(uri.getVersion())
                .buildInfo(buildInfoBuilder.build());

        return builder.build();
    }
}
