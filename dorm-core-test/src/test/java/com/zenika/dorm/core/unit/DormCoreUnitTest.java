package com.zenika.dorm.core.unit;

import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.core.model.old.DormFile;
import com.zenika.dorm.core.model.old.DormMetadata;
import com.zenika.dorm.core.model.old.MetadataExtension;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @deprecated
 */
abstract public class DormCoreUnitTest {

    protected DormArtifact<MetadataExtension> artifact1;
    protected DormArtifact<MetadataExtension> artifact2;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        createModels();
    }

    private void createModels() {

        DormMetadata<MetadataExtension> metadata1 = new DormMetadata<MetadataExtension>("Foo1", "1.0");
        DormMetadata<MetadataExtension> metadata2 = new DormMetadata<MetadataExtension>("Foo2", "2.0");

        DormFile file1 = new DormFile();
        file1.setName("Foo");
        file1.setExtension("jar");

        DormFile file2 = new DormFile();
        file2.setName("Foo");
        file2.setExtension("pom");

        try {
            File tmpFile1 = File.createTempFile("foo", ".jar");
            tmpFile1.deleteOnExit();

            File tmpFile2 = File.createTempFile("foo", ".pom");
            tmpFile2.deleteOnExit();

            file1.setFile(tmpFile1);
            file2.setFile(tmpFile2);

        } catch (IOException e) {
            e.printStackTrace();
        }

        artifact1 = new DormArtifact<MetadataExtension>(metadata1, file1);
        artifact2 = new DormArtifact<MetadataExtension>(metadata2, file2);
    }
}