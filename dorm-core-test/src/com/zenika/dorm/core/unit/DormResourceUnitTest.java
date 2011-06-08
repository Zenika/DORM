package com.zenika.dorm.core.unit;

import com.zenika.dorm.core.helper.DormFileHelper;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.ws.resource.DormResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormResourceUnitTest {

    @Mock
    private DormService service;

    @InjectMocks
    private DormResource resource = new DormResource();

    private File file;
    private File properties;

    private String name;
    private String version;
    private String filename;
    private DormMetadata<MetadataExtension> metadata;
    private DormFile dormFile;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        createModels();
    }

    @After
    public void after() {
        file.delete();
        properties.delete();
    }

    private void createModels() {

        name = "Foo";
        version = "1.0";
        filename = "foo-1.0.jar";

        try {
            file = File.createTempFile("/tmp", filename);
            properties = getPropertiesFile(name, version, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        metadata = new DormMetadata<MetadataExtension>(name, version, DormService.ORIGIN);
        dormFile = DormFileHelper.createDormFileFromFilename(metadata, file, filename);

    }

    private File getPropertiesFile(String name, String version, String filename) {

        File propertiesFile = new File("/tmp", name + "-" + version);
        Properties properties = new Properties();
        properties.put("name", name);
        properties.put("version", version);
        properties.put("filename", filename);

        try {
            properties.store(new FileOutputStream(propertiesFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return propertiesFile;
    }

    @Test
    public void createArtifact() {
        resource.createArtifactFromPath(name, version, file, filename);
        resource.createArtifactFromProperties(properties, file);
        verify(service, times(2)).pushArtifact(metadata, file, filename);
    }

    @Test
    public void getArtifact() {
        resource.getArtifactByMetadata(name, version);
        verify(service).getArtifact(metadata);
    }

    @Test
    public void removeArtifact() {
        resource.removeArtifactByMetadata(name, version);
        verify(service).removeArtifact(metadata);
    }

    @Test
    public void updateArtifact() {

        File file2 = null;
        String filename2 = "foo2-1.0.jar";

        try {
            file2 = File.createTempFile("/tmp", filename2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File properties2 = getPropertiesFile(name, version, filename2);

        resource.updateArtifact(name, version, file2, filename2);
        resource.updateArtifact(properties2, file2);

        verify(service, times(2)).updateArtifact(metadata, file2, filename2);
    }
}
