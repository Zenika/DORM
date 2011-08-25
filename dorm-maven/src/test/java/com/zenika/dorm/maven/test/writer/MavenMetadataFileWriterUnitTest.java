package com.zenika.dorm.maven.test.writer;

import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import com.zenika.dorm.maven.writer.MavenMetadataFileWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataFileWriterUnitTest extends AbstractUnitTest {

    @Test
    public void writeMavenMetadata() throws IOException {

        File folders = new File("tmp/test/maven");
        folders.mkdirs();

        File file = new File(folders, "maven-metadata.xml");
        file.createNewFile();

        MavenMetadataFileWriter writer = new MavenMetadataFileWriter(file);
        writer.write((MavenMetadataExtension) fixtures.getSnapshotMetadataExtension());
    }
}
