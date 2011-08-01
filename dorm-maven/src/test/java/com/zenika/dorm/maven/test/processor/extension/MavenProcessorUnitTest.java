package com.zenika.dorm.maven.test.processor.extension;


import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.processor.RequestProcessor;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the maven processor
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenProcessorUnitTest extends AbstractUnitTest {

    @Mock
    private RequestProcessor requestProcessor;

    @InjectMocks
    private MavenProcessor processor = new MavenProcessor();

    @Test
    public void pushStandardMavenArtifact() {

        DependencyNode entityNode = fixtures.getEntityNodeWithChildAndFile();
        DormMetadataExtension entityExtension = fixtures.getEntityExtension();
        DormRequest request = fixtures.getRequestWithFile();

        given(requestProcessor.createNode(entityExtension, request)).willReturn(entityNode);
        Assertions.assertThat(processor.push(request)).isEqualTo(entityNode);
        verify(requestProcessor).createNode(entityExtension, request);
    }

}
