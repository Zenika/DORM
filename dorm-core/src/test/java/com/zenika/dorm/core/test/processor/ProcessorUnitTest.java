package com.zenika.dorm.core.test.processor;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormRequest;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.processor.impl.DefaultProcessor;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the processor
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ProcessorUnitTest extends AbstractUnitTest {

    @Mock
    private ProcessorExtension extension;

    @Mock
    private Map<String, ProcessorExtension> extensions;

    @Mock
    private DormService service;

    @InjectMocks
    private Processor processor = new DefaultProcessor();

    @Override
    public void before() {
        super.before();

        // mock & stub test processor extension
        given(extensions.get("test")).willReturn(extension);
    }

    @Test
    public void push() {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(DormRequest.ORIGIN, "test");
        properties.put(DormRequest.VERSION, "1.0");

        DormRequest request = DefaultDormRequest.create(properties);

        DependencyNode node = DefaultDependencyNode.create(DefaultDependency.create(
                DefaultDormMetadata.create("1.0", new DefaultDormMetadataExtension("test"))));

        given(extension.push(request)).willReturn(node);

        given(service.pushNode(node)).willReturn(true);
        Assertions.assertThat(processor.push(request)).isTrue();

        verify(extension).push(request);
        verify(service).pushNode(node);
    }

    @Test(expected = CoreException.class)
    public void pushWithUnkownExtension() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(DormRequest.ORIGIN, "foo");
        properties.put(DormRequest.VERSION, "1.0");
        DormRequest request = DefaultDormRequest.create(properties);
        processor.push(request);
    }

    @Test(expected = CoreException.class)
    public void pushNullProperty() {
        processor.push(null);
    }
}
