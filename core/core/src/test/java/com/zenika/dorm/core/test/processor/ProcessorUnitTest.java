package com.zenika.dorm.core.test.processor;

import com.zenika.dorm.core.processor.DormProcessor;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.junit.Ignore;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Map;

import static org.mockito.BDDMockito.given;

/**
 * Unit tests for the processor
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class ProcessorUnitTest extends AbstractUnitTest {

    @Mock
    private ProcessorExtension extension;

    @Mock
    private Map<String, ProcessorExtension> extensions;

    @Mock
    private DormService service;

    @InjectMocks
    private DormProcessor processor = new DormProcessor();

    @Override
    public void before() {
        super.before();

        // mock & stub test processor extension
        given(extensions.get("test")).willReturn(extension);
    }

//    @Test
//    public void pushValidArtifact() {
//
//        DormRequest request = fixtures.getRequestBuilder()
//                .origin("test")
//                .build();
//
//        DependencyNode node = fixtures.getNodeWithResource();
//
//        // extension return the correct node corresponding to the request
//        given(extension.put(request)).willReturn(node);
//
//        // service stores the node successfully
//        given(service.put(node)).willReturn(true);
//
//        // if store was done successfully then processor should also return true
//        Assertions.assertThat(processor.put(request)).isTrue();
//
//        verify(extension).put(request);
//        verify(service).put(node);
//    }
//
//    @Test(expected = CoreException.class)
//    public void pushWithUnkownExtension() {
//
//        DormRequest request = fixtures.getRequestBuilder()
//                .origin("foo")
//                .build();
//
//        processor.put(request);
//    }
}
