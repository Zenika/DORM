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

        MavenMetadataBuilder builder = new MavenMetadataBuilder(uri.getArtifactId())
                .groupId(uri.getGroupId())
                .version(uri.getVersion())
                .snapshot(uri.isSnapshot())
                .extension(filename.getExtension());

        if (StringUtils.isNotBlank(filename.getClassifier())) {
            builder.classifier(filename.getClassifier());
        }

        if (StringUtils.isNotBlank(filename.getTimestamp()) && StringUtils.isNotBlank(filename
                .getBuildNumber())) {
            builder.timestamp(filename.getTimestamp());
            builder.buildNumber(filename.getBuildNumber());
        }

        return builder.build();
    }
}
