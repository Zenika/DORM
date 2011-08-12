package com.zenika.dorm.maven.test.ws.resource;

import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import com.zenika.dorm.maven.ws.resource.MavenResource;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenResourceUnitTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenResourceUnitTest.class);

    @Mock
    private Processor processor;

    @InjectMocks
    private MavenResource resource = new MavenResource();

    @Test
    public void pushValidMetadata() {

        DormRequest request = fixtures.getRequestWithFile();
        LOG.trace("Test request = " + request);

        when(processor.push(request)).thenReturn(true);

        Response response = resource.put(fixtures.getFile(), fixtures.getGroupId(),
                fixtures.getArtifactId(), fixtures.getMavenVersion(), fixtures.getFilenameWithExtension());

        verify(processor).push(request);

        Assertions.assertThat(Response.Status.fromStatusCode(response.getStatus()))
                .isEqualTo(Response.Status.OK);
    }

    @Test(expected = MavenException.class)
    public void pushInvalidMetadata() {

        DormRequest request = new DormRequestBuilder(fixtures.getRequestWithFile())
                .property(MavenMetadataExtension.METADATA_ARTIFACTID, null)
                .build();

        LOG.trace("Test request = " + request);

        when(processor.push(request)).thenThrow(new MavenException());

        resource.put(fixtures.getFile(), fixtures.getGroupId(),
                null, fixtures.getMavenVersion(), fixtures.getFilenameWithExtension());
        verify(processor).push(request);
    }

    @Test(expected = MavenException.class)
    @Ignore 
    public void pushValidMetadataWithoutFile() {

        DormRequest request = fixtures.getRequestWithoutFile();
        LOG.trace("Test request = " + request);

        when(processor.push(request)).thenThrow(new MavenException());

        resource.put(null, fixtures.getGroupId(),
                fixtures.getArtifactId(), fixtures.getMavenVersion(), fixtures.getFilenameWithExtension());

        verify(processor).push(request);
    }

    public void getWithValidMetadata() {

        DormRequest request = fixtures.getRequestWithoutFile();
        LOG.trace("Test request = " + request);

        when(processor.get(request)).thenReturn(fixtures.getDependencyWithFile());

        Response response = resource.get(fixtures.getGroupId(), fixtures.getArtifactId(),
                fixtures.getMavenVersion(), fixtures.getFilenameWithExtension());

        Assertions.assertThat(Response.Status.fromStatusCode(response.getStatus()))
                .isEqualTo(Response.Status.OK);

        Assertions.assertThat(response.getEntity()).isEqualTo(fixtures.getFile());
    }

    @Test(expected = MavenException.class)
    @Ignore
    public void getWithInvalidMetadata() {

        DormRequest request = new DormRequestBuilder(fixtures.getRequestWithoutFile())
                .property(MavenMetadataExtension.METADATA_ARTIFACTID, null)
                .build();

        LOG.trace("Test request = " + request);

        when(processor.get(request)).thenThrow(new MavenException());

        Response response = resource.get(fixtures.getGroupId(), null, fixtures.getMavenVersion(),
                fixtures.getFilenameWithExtension());

        verify(processor).get(request);
    }
}
