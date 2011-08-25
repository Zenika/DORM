package com.zenika.dorm.maven.builder;

import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.Snapshot;
import org.apache.maven.artifact.repository.metadata.Versioning;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataBuilder {

    public static Metadata buildMetadata(MavenMetadataExtension dormMetadata) {

        Metadata metadata = new Metadata();
        metadata.setArtifactId(dormMetadata.getArtifactId());
        metadata.setGroupId(dormMetadata.getGroupId());
        metadata.setVersion(dormMetadata.getVersion());

        Versioning versioning = new Versioning();
        metadata.setVersioning(versioning);

        if (dormMetadata.isSnapshot()) {
            Snapshot snapshot = new Snapshot();
            snapshot.setBuildNumber(Integer.valueOf(dormMetadata.getBuildNumber()));
            snapshot.setTimestamp(dormMetadata.getTimestamp());
            versioning.setSnapshot(snapshot);
        }

        return metadata;
    }
}
