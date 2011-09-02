package com.zenika.dorm.core.test.processor.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.processor.impl.DormProcessor;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;

/**
 * Unit tests for the dorm processor extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class DormProcessorUnitTest extends AbstractUnitTest {


    @InjectMocks
    private DormProcessor processor = new DormProcessor();

    @Test
    public void pushStandardDormArtifact() {

        DependencyNode node = fixtures.getNodeWithResource();
        DormWebServiceRequest request = fixtures.getRequestWithFile();

        Assertions.assertThat(processor.push(request)).isEqualTo(node);
    }

    @Test(expected = CoreException.class)
    public void pushDormArtifactWithoutRequiredMetadatas() {

        DormWebServiceRequest request = fixtures.getRequestBuilderWithFile()
                .property(DefaultDormMetadataExtension.METADATA_NAME, null)
                .build();

        processor.push(request);
    }
}
