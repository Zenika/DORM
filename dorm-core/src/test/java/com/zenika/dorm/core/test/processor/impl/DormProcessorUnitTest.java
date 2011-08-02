package com.zenika.dorm.core.test.processor.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormRequest;
import com.zenika.dorm.core.processor.impl.DormProcessor;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Map;

/**
 * Unit tests for the dorm processor extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessorUnitTest extends AbstractUnitTest {


    @InjectMocks
    private DormProcessor processor = new DormProcessor();

    @Test
    public void pushStandardDormArtifact() {

        DependencyNode node = fixtures.getNodeWithFile();
        DormMetadataExtension extension = fixtures.getMetadataExtension();
        DormRequest request = fixtures.getRequestWithFile();

//        given(factoryFromRequest.createNode(extension, request)).willReturn(node);

        Assertions.assertThat(processor.push(request)).isEqualTo(node);

//        verify(factoryFromRequest).createNode(extension, request);
    }

    @Test(expected = CoreException.class)
    public void pushDormArtifactWithoutFile() {
        processor.push(fixtures.getRequestWithoutFile());
    }

    @Test(expected = CoreException.class)
    public void pushDormArtifactWithoutRequiredMetadatas() {

        Map<String, String> properties = fixtures.getRequestPropertiesWithFile();
        properties.put(DormProcessor.METADATA_NAME, null);

        DormRequest request = DefaultDormRequest.create(properties, fixtures.getFile());

        processor.push(request);
    }
}
