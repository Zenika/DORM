package com.zenika.dorm.maven.builder;

import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.Snapshot;
import org.apache.maven.artifact.repository.metadata.SnapshotVersion;
import org.apache.maven.artifact.repository.metadata.Versioning;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataBuilder {

    public static Metadata buildMetadata(MavenMetadataExtension dormMetadata) {

        Metadata metadata = new Metadata();
        metadata.setArtifactId(dormMetadata.getArtifactId());
        metadata.setGroupId(dormMetadata.getGroupId());
        metadata.setVersion(dormMetadata.getVersion());

        SnapshotVersion snapshotVersion = new SnapshotVersion();
        snapshotVersion.setVersion("1.0.1");

        List<SnapshotVersion> snapshotVersions = new ArrayList<SnapshotVersion>();
        snapshotVersions.add(snapshotVersion);

        Snapshot snapshot = new Snapshot();
        snapshot.setBuildNumber(1);
        snapshot.setTimestamp("1243");

        Versioning versioning = new Versioning();
//        versioning.setSnapshotVersions(snapshotVersions);
        versioning.setSnapshot(snapshot);

        metadata.setVersioning(versioning);
//        metadata.set

        return metadata;
    }
}
