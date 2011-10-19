package com.zenika.dorm.maven.converter;

import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.Snapshot;
import org.apache.maven.artifact.repository.metadata.Versioning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MavenDormMetadataConverter {

    private static final Logger LOG = LoggerFactory.getLogger(MavenDormMetadataConverter.class);

    public static Metadata dormToMaven(MavenMetadata dormMetadata) {

        Metadata metadata = new Metadata();
        metadata.setArtifactId(dormMetadata.getArtifactId());
        metadata.setGroupId(dormMetadata.getGroupId());
        metadata.setVersion(dormMetadata.getVersion());

        Versioning versioning = new Versioning();
        metadata.setVersioning(versioning);

        if (dormMetadata.isSnapshot()) {
            Snapshot snapshot = new Snapshot();
            snapshot.setBuildNumber(Integer.valueOf(dormMetadata.getBuildInfo().getBuildNumber()));
            snapshot.setTimestamp(dormMetadata.getBuildInfo().getTimestamp());
            versioning.setSnapshot(snapshot);
        }

        return metadata;
    }
}
