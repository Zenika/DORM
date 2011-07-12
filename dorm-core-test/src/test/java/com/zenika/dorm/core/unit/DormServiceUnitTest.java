package com.zenika.dorm.core.unit;

import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.core.model.old.MetadataExtension;
import com.zenika.dorm.core.service.impl.DormServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */

public class DormServiceUnitTest extends DormCoreUnitTest {

    @Mock
    private DormDao dao;

    @InjectMocks
    private DormServiceImpl service = new DormServiceImpl();

    @Test(expected = ArtifactException.class)
    public void pushArtifactShouldFail() {
        artifact1.setMetadata(null);
        service.pushArtifact(artifact1);
    }

    /**
     * Test the all 4 pushArtifact methods
     */
    @Test
    public void pushArtifact() {
        given(dao.getByMetadata(artifact1.getMetadata())).willThrow(new RepositoryException("Not found").type(RepositoryException.Type.NULL));
        given(dao.save(artifact1)).willReturn(artifact1);

        DormArtifact<MetadataExtension> artifact11 = service.pushArtifact(artifact1);
        DormArtifact<MetadataExtension> artifact12 = service.pushArtifact(artifact1.getMetadata(), artifact1.getFile().getFile(), artifact1.getFile().getFilename());
        DormArtifact<MetadataExtension> artifact13 = service.pushArtifact(artifact1.getMetadata(), artifact1.getFile());
        DormArtifact<MetadataExtension> artifact14 = service.pushArtifact(artifact1.getMetadata(), artifact1.getFile(), new ArrayList<DormArtifact<MetadataExtension>>());

        verify(dao, times(4)).save(artifact1);

        assertThat(artifact1).as("Artifact").isEqualTo(artifact11).as("Artifact from push 1");
        assertThat(artifact1).as("Artifact").isEqualTo(artifact12).as("Artifact from push 2");
        assertThat(artifact1).as("Artifact").isEqualTo(artifact13).as("Artifact from push 3");
        assertThat(artifact1).as("Artifact").isEqualTo(artifact14).as("Artifact from push 4");
    }

    @Test(expected = ArtifactException.class)
    public void pushExistingArtifactShouldFail() {
        given(dao.getByMetadata(artifact1.getMetadata())).willReturn(artifact1);
        service.pushArtifact(artifact1);
    }

    @Test
    public void removeArtifactShouldRemoveArtifact() {
        service.removeArtifact(artifact1.getMetadata());
        service.removeArtifact(artifact1);

        verify(dao, times(2)).removeByMetadata(artifact1.getMetadata());
    }

    @Test
    public void getArtifactShouldReturnArtifact() {
        given(dao.getByMetadata(artifact1.getMetadata())).willReturn(artifact1);
        DormArtifact<MetadataExtension> res = service.getArtifact(artifact1.getMetadata());
        assertThat(artifact1).as("Artifact").isEqualTo(res).as("Artifact from get");
    }

    @Test
    public void updateArtifact() {
        given(dao.save(artifact1)).willReturn(artifact1);
        DormArtifact<MetadataExtension> res = service.updateArtifact(artifact1.getMetadata(), artifact1.getFile().getFile(), artifact1.getFile().getFilename());
        DormArtifact<MetadataExtension> res2 = service.updateArtifact(artifact1);
        verify(dao, times(2)).save(artifact1);
        assertThat(artifact1).as("Artifact").isEqualTo(res).as("Artifact from update 1");
        assertThat(artifact1).as("Artifact").isEqualTo(res2).as("Artifact from update 2");
    }

    @Test(expected = ArtifactException.class)
    public void updateNonExistingArtifactShouldFail() {
        given(dao.getByMetadata(artifact1.getMetadata())).willThrow(new RepositoryException("Not found").type(RepositoryException.Type.NULL));
        service.updateArtifact(artifact1);
    }
}
