package com.zenika.dorm.core.test.resource;

import com.zenika.dorm.core.model.DormWebServiceRequest;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import com.zenika.dorm.core.ws.resource.DormResource;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.verify;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class DormResourceUnitTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(DormResourceUnitTest.class);

    @Mock
    private Processor processor;

    @InjectMocks
    private DormResource resource = new DormResource();

    @Test
    public void pushValidMetadata() {

        DormWebServiceRequest request = fixtures.getRequestWithoutFile();
        LOG.trace("Test request = " + request);

        when(processor.push(request)).thenReturn(true);

        Response response = resource.pushMetadata(fixtures.getName(), fixtures.getVersion());
        verify(processor).push(request);

        Assertions.assertThat(Response.Status.fromStatusCode(response.getStatus()))
                .isEqualTo(Response.Status.OK);
    }

    @Test
    public void pushInvalidMetadata() {

        DormWebServiceRequest request = new DormRequestBuilder(fixtures.getRequestWithoutFile()).property
                (DefaultDormMetadataExtension.METADATA_NAME, null).build();

        LOG.trace("Test request = " + request);

        when(processor.push(request)).thenReturn(false);

        Response response = resource.pushMetadata(null, fixtures.getVersion());
        verify(processor).push(request);

        Assertions.assertThat(Response.Status.fromStatusCode(response.getStatus())).isEqualTo(Response
                .Status.NOT_FOUND);
    }

    @Test
    public void pushValidMetadataAndFile() {

        DormWebServiceRequest request = fixtures.getRequestWithFile();
        LOG.trace("Test request = " + request);

        when(processor.push(request)).thenReturn(true);

        Response response = resource.pushMetadataAndFile(fixtures.getName(), fixtures.getVersion(),
                fixtures.getFilenameWithExtension(), fixtures.getFile());
        verify(processor).push(request);

        Assertions.assertThat(Response.Status.fromStatusCode(response.getStatus()))
                .isEqualTo(Response.Status.OK);
    }
}
