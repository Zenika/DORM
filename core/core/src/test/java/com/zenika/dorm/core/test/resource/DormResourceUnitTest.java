package com.zenika.dorm.core.test.resource;

import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class DormResourceUnitTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(DormResourceUnitTest.class);

//    @Mock
//    private Processor processor;
//
//    @InjectMocks
//    private DormResource resource = new DormResource();
//
//    @Test
//    public void pushValidMetadata() {
//
//        DormWebServiceRequest request = fixtures.getRequestWithoutFile();
//        LOG.trace("Test request = " + request);
//
//        when(processor.push(request)).thenReturn(true);
//
//        Response response = resource.pushMetadata(fixtures.getName(), fixtures.getVersion());
//        verify(processor).push(request);
//
//        Assertions.assertThat(Response.Status.fromStatusCode(response.getStatus()))
//                .isEqualTo(Response.Status.OK);
//    }
//
//    @Test
//    public void pushInvalidMetadata() {
//
//        DormWebServiceRequest request = new DormWebServiceRequestBuilder(fixtures.getRequestWithoutFile()).property
//                (DefaultDormMetadataExtension.METADATA_NAME, null).build();
//
//        LOG.trace("Test request = " + request);
//
//        when(processor.push(request)).thenReturn(false);
//
//        Response response = resource.pushMetadata(null, fixtures.getVersion());
//        verify(processor).push(request);
//
//        Assertions.assertThat(Response.Status.fromStatusCode(response.getStatus())).isEqualTo(Response
//                .Status.NOT_FOUND);
//    }
//
//    @Test
//    public void pushValidMetadataAndFile() {
//
//        DormWebServiceRequest request = fixtures.getRequestWithFile();
//        LOG.trace("Test request = " + request);
//
//        when(processor.push(request)).thenReturn(true);
//
//        Response response = resource.pushMetadataAndFile(fixtures.getName(), fixtures.getVersion(),
//                fixtures.getFilenameWithExtension(), fixtures.getFile());
//        verify(processor).push(request);
//
//        Assertions.assertThat(Response.Status.fromStatusCode(response.getStatus()))
//                .isEqualTo(Response.Status.OK);
//    }
}
