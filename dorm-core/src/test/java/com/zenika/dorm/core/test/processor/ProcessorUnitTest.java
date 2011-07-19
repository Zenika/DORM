package com.zenika.dorm.core.test.processor;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.DormProperties;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;
import com.zenika.dorm.core.model.impl.DefaultDormProperties;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.processor.impl.DefaultProcessor;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

        DormProperties properties = new DefaultDormProperties("1.0", "test");

        DependencyNode node = new DefaultDependencyNode(new DefaultDependency(
                new DefaultDormMetadata("1.0", new DefaultDormOrigin("test"))));

        given(extension.push(properties)).willReturn(node);

        given(service.pushNode(node)).willReturn(true);
        Assertions.assertThat(processor.push(properties)).isTrue();

        verify(extension).push(properties);
        verify(service).pushNode(node);
    }

    @Test(expected = CoreException.class)
    public void pushWithUnkownExtension() {
        DormProperties properties = new DefaultDormProperties("1.0", "foo");
        processor.push(properties);
    }

    @Test(expected = CoreException.class)
    public void pushNullProperty() {
        processor.push(null);
    }
}
