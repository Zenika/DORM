package com.zenika.dorm.core.unit;

import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.core.model.old.DormFile;
import com.zenika.dorm.core.model.old.DormMetadata;
import com.zenika.dorm.core.model.old.MetadataExtension;
import com.zenika.dorm.core.service.DormServiceOld;
import com.zenika.dorm.core.ws.resource.DormResource;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @deprecated test based on the old version of core model
 */
public class DormResourceUnitTest extends DormCoreUnitTest {

    @Mock
    private DormServiceOld service;

    @InjectMocks
    private DormResource resource = new DormResource();

    /**
     * Proxy method to getPropertiesFile with name, version and filename
     * These informations are taken from artifact
     *
     * @param artifact the artifact from which extract the name, version and filename
     * @return the properties file
     */
    private File getPropertiesFile(DormArtifact<MetadataExtension> artifact) {
        return getPropertiesFile(artifact.getMetadata().getName(), artifact.getMetadata().getVersion(), artifact.getFile().getFilename());
    }

    /**
     * Create new properties file based on the given name, version and filename
     *
     * @param name     the artifact name
     * @param version  the artifact version
     * @param filename the artifact's file name
     * @return the properties file
     */
    private File getPropertiesFile(String name, String version, String filename) {

        File propertiesFile = null;
        try {
            propertiesFile = File.createTempFile(name + "-" + version, ".properties");
            propertiesFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        DormMetadata<MetadataExtension> metadata = artifact1.getMetadata();
        DormFile file = artifact1.getFile();
        File properties = getPropertiesFile(artifact1);

//        resource.createArtifactFromPath(metadata.getName(), metadata.getVersion(), file.getFile(), file.getFilename());
        resource.createArtifactFromProperties(properties, file.getFile());

        verify(service, times(2)).pushArtifact(metadata, file.getFile(), file.getFilename());
    }

    @Test
    public void getArtifact() {
        resource.getArtifactByMetadata(artifact1.getMetadata().getName(), artifact1.getMetadata().getVersion());
        verify(service).getArtifact(artifact1.getMetadata());
    }

    @Test
    public void removeArtifact() {
        resource.removeArtifactByMetadata(artifact1.getMetadata().getName(), artifact1.getMetadata().getVersion());
        verify(service).removeArtifact(artifact1.getMetadata());
    }

    /**
     * Update artifact should only update artifact file not metadata
     */
    @Test
    public void updateArtifact() {

        DormMetadata<MetadataExtension> metadata = artifact1.getMetadata();
        DormFile file = artifact2.getFile();

        File properties2 = getPropertiesFile(metadata.getName(), metadata.getVersion(), file.getFilename());

        resource.updateArtifact(metadata.getName(), metadata.getVersion(), file.getFile(), file.getFilename());
        resource.updateArtifact(properties2, file.getFile());

        verify(service, times(2)).updateArtifact(metadata, file.getFile(), file.getFilename());
    }
}
