package com.zenika.dorm.maven.converter;

import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.Snapshot;
import org.apache.maven.artifact.repository.metadata.Versioning;
import org.apache.maven.artifact.repository.metadata.io.xpp3.MetadataXpp3Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenDormMetadataConverter {

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

    public static MavenMetadata mavenToDorm(File mavenMetadataFile) {

        Metadata metadata;

        try {
            metadata = new MetadataXpp3Reader().read(new FileInputStream(mavenMetadataFile));
        } catch (Exception e) {
            LOG.error("Maven metadata file cannot be read", e);
            throw new MavenException("Maven metadata file cannot be read", e);
        }

        return mavenToDorm(metadata);
    }

    public static MavenMetadata mavenToDorm(Metadata metadata) {

        if (null != metadata.getPlugins() && !metadata.getPlugins().isEmpty()) {
            throw new UnsupportedOperationException("Maven plugins are not yet supported");
        }

        MavenMetadataBuilder builder = new MavenMetadataBuilder(metadata.getArtifactId())
                .groupId(metadata.getGroupId());

//        metadata.get
        return null;
    }

}
