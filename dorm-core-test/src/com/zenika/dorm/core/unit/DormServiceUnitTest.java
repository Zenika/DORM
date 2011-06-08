package com.zenika.dorm.core.unit;

import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;
import com.zenika.dorm.core.service.impl.DormServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

public class DormServiceUnitTest {

    @Mock
    private DormDao dao;

    @InjectMocks
    private DormServiceImpl service = new DormServiceImpl();

    private DormArtifact<MetadataExtension> artifact;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        createModels();
    }

    private void createModels() {
        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>("Foo", "1.0", "Bar");
        DormFile file = new DormFile();
        file.setName("Foo.jar");
        file.setExtension("jar");

        try {
            file.setFile(File.createTempFile("/tmp", "foo.jar"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        artifact = new DormArtifact<MetadataExtension>(metadata, file);
    }

    @Test(expected = ArtifactException.class)
    public void pushArtifactShouldFail() {
        artifact.setMetadata(null);
        service.pushArtifact(artifact);
    }

    /**
     * Test the all 4 pushArtifact methods
     */
    @Test
    public void pushArtifact() {
        given(dao.getByMetadata(artifact.getMetadata())).willThrow(new RepositoryException("Not found").type(RepositoryException.Type.NULL));
        given(dao.save(artifact)).willReturn(artifact);

        DormArtifact<MetadataExtension> artifact1 = service.pushArtifact(artifact);
        DormArtifact<MetadataExtension> artifact2 = service.pushArtifact(artifact.getMetadata(), artifact.getFile().getFile(), artifact.getFile().getFilename());
        DormArtifact<MetadataExtension> artifact3 = service.pushArtifact(artifact.getMetadata(), artifact.getFile());
        DormArtifact<MetadataExtension> artifact4 = service.pushArtifact(artifact.getMetadata(), artifact.getFile(), new ArrayList<DormArtifact<MetadataExtension>>());

        verify(dao, times(4)).save(artifact);

        assertThat(artifact).as("Artifact").isEqualTo(artifact1).as("Artifact from push 1");
        assertThat(artifact).as("Artifact").isEqualTo(artifact2).as("Artifact from push 2");
        assertThat(artifact).as("Artifact").isEqualTo(artifact3).as("Artifact from push 3");
        assertThat(artifact).as("Artifact").isEqualTo(artifact4).as("Artifact from push 4");
    }

    @Test(expected = ArtifactException.class)
    public void pushExistingArtifactShouldFail() {
        given(dao.getByMetadata(artifact.getMetadata())).willReturn(artifact);
        service.pushArtifact(artifact);
    }

    @Test
    public void removeArtifactShouldRemoveArtifact() {
        service.removeArtifact(artifact.getMetadata());
        service.removeArtifact(artifact);

        verify(dao, times(2)).removeByMetadata(artifact.getMetadata());
    }

    @Test
    public void getArtifactShouldReturnArtifact() {
        given(dao.getByMetadata(artifact.getMetadata())).willReturn(artifact);
        DormArtifact<MetadataExtension> res = service.getArtifact(artifact.getMetadata());
        assertThat(artifact).as("Artifact").isEqualTo(res).as("Artifact from get");
    }

    @Test
    public void updateArtifact() {
        given(dao.save(artifact)).willReturn(artifact);
        DormArtifact<MetadataExtension> res = service.updateArtifact(artifact.getMetadata(), artifact.getFile().getFile(), artifact.getFile().getFilename());
        DormArtifact<MetadataExtension> res2 = service.updateArtifact(artifact);
        verify(dao, times(2)).save(artifact);
        assertThat(artifact).as("Artifact").isEqualTo(res).as("Artifact from update 1");
        assertThat(artifact).as("Artifact").isEqualTo(res2).as("Artifact from update 2");
    }

    @Test(expected = ArtifactException.class)
    public void updateNonExistingArtifactShouldFail() {
        given(dao.getByMetadata(artifact.getMetadata())).willThrow(new RepositoryException("Not found").type(RepositoryException.Type.NULL));
        service.updateArtifact(artifact);
    }
}
