package com.zenika.dorm.maven.test.processor.extension;


import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;

/**
 * Unit tests for the maven processor
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenProcessorUnitTest extends AbstractUnitTest {

    @InjectMocks
    private MavenProcessor processor = new MavenProcessor();

    @Test
    @Ignore
    public void pushStandardMavenArtifact() {

        DependencyNode entityNode = fixtures.getEntityNodeWithChildAndFile();
        DormMetadataExtension entityExtension = fixtures.getEntityExtension();
        DormRequest request = fixtures.getRequestWithFile();

//        given(factoryFromRequest.createNode(entityExtension, request)).willReturn(entityNode);
        Assertions.assertThat(processor.push(request)).isEqualTo(entityNode);
//        verify(factoryFromRequest).createNode(entityExtension, request);
    }

}
