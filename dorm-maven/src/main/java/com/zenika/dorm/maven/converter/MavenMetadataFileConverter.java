package com.zenika.dorm.maven.converter;

import com.zenika.dorm.maven.exception.MavenException;
import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.io.xpp3.MetadataXpp3Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataFileConverter {

    private static final Logger LOG = LoggerFactory.getLogger(MavenMetadataFileConverter.class);

    private Metadata metadata;

    public MavenMetadataFileConverter(File mavenMetadataFile) {
        try {
            metadata = new MetadataXpp3Reader().read(new FileInputStream(mavenMetadataFile));
        } catch (Exception e) {
            LOG.error("Maven metadata file cannot be read", e);
            throw new MavenException("Maven metadata file cannot be read", e);
        }
    }

//    public MavenMetadataExtension getDormMavenMetadata() {
//
//        return new MavenMetadataBuilder(metadata.getArtifactId())
//                .groupId(metadata.getGroupId())
//                .version(metadata.getVersion());
//    }
}
