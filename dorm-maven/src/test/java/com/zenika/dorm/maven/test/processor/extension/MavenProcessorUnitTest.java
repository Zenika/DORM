package com.zenika.dorm.maven.test.processor.extension;


import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the maven processor
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class MavenProcessorUnitTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProcessorUnitTest.class);

    @InjectMocks
    private MavenProcessor processor = new MavenProcessor();

    @Test
    public void pushStandardMavenArtifact() {

        DependencyNode entityNode = fixtures.getEntityNodeWithChild();
        DependencyNode childNode = entityNode.getChildren().iterator().next();
        DormRequest request = fixtures.getRequestWithFile();

        LOG.trace("Test entity node = " + entityNode.getDependency());
        LOG.trace("Test real dependency node = " + childNode.getDependency());
        LOG.trace("Test request = " + request);

        DependencyNode pushedNode = processor.push(request);

        Assertions.assertThat(pushedNode).as("Entity node").isEqualTo(entityNode);
        Assertions.assertThat(pushedNode.getChildren()).as("Real dependency node").
                isEqualTo(entityNode.getChildren());
    }


    @Test(expected = MavenException.class)
    public void pushMaventArtifactWithoutFile() {
        processor.push(fixtures.getRequestWithoutFile());
    }

    @Test(expected = MavenException.class)
    public void pushDormArtifactWithoutRequiredMetadatas() {

        DormRequest request = new DormRequestBuilder(fixtures.getRequestWithFile())
                .property(MavenMetadataExtension.METADATA_ARTIFACTID, null)
                .build();

        processor.push(request);
    }

//    @Test
//    public void getMavenMetadataFromRequest() {
//        DormMetadata metadata = processor.getMetadata(fixtures.getRequestWithFilename());
//        Assertions.assertThat(metadata).isEqualTo(fixtures.getMetadata());
//    }

}
