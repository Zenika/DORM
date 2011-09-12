package com.zenika.dorm.maven.writer;

import com.zenika.dorm.maven.converter.MavenDormMetadataConverter;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.io.xpp3.MetadataXpp3Writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataFileWriter {

    private File file;

    public MavenMetadataFileWriter(File file) {
        this.file = file;
    }

    public File write(MavenMetadataExtension metadata) {

        MetadataXpp3Writer writer = new MetadataXpp3Writer();
        Metadata mavenMetadata = MavenDormMetadataConverter.dormToMaven(metadata);

        try {
            writer.write(new FileOutputStream(file), mavenMetadata);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
