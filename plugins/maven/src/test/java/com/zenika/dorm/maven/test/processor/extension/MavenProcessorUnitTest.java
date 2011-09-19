package com.zenika.dorm.maven.test.processor.extension;


import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.service.MavenService;
import com.zenika.dorm.maven.test.fixtures.MavenWebServiceRequestFixtures;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the maven processor
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenProcessorUnitTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProcessorUnitTest.class);

    private MavenWebServiceRequestFixtures webServiceRequestFixtures;

    @Mock
    private MavenMetadataBuilder mavenMetadataBuilder;

    @Mock
    private MavenService mavenService;

    @InjectMocks
    private MavenProcessor processor = new MavenProcessor();

    @Override
    public void before() {
        super.before();
        webServiceRequestFixtures = new MavenWebServiceRequestFixtures(pathFixtures);
    }

    @Test
    public void pushJar() {

        DormWebServiceRequest request = webServiceRequestFixtures.getSimpleJar();
        MavenMetadata metadata = fixtures.getSimpleJar();

        when(mavenMetadataBuilder.build()).thenReturn(metadata);

        processor.push(request);

        verify(mavenService).storeMetadataWithArtifact(metadata, request.getFile());
    }

    @Test
    public void pushJarSha1Hash() {

        DormWebServiceRequest request = webServiceRequestFixtures.getSimpleJarSha1();
        MavenMetadata metadata = fixtures.getSimpleJarSha1();

        when(mavenMetadataBuilder.build()).thenReturn(metadata);

        processor.push(request);

        verify(mavenService).storeHash(metadata, request.getFile());
    }

    @Test
    public void pushJarMd5Hash() {

        DormWebServiceRequest request = webServiceRequestFixtures.getSimpleJarMd5();
        MavenMetadata metadata = fixtures.getSimpleJarMd5();

        when(mavenMetadataBuilder.build()).thenReturn(metadata);

        processor.push(request);

        verify(mavenService).storeHash(metadata, request.getFile());
    }

    @Test
    public void pushPom() {

        DormWebServiceRequest request = webServiceRequestFixtures.getSimplePom();
        MavenMetadata metadata = fixtures.getSimplePom();

        when(mavenMetadataBuilder.build()).thenReturn(metadata);

        processor.push(request);

        verify(mavenService).storePom(metadata, request.getFile());
    }

    @Test
    public void pushPomSha1Hash() {

        DormWebServiceRequest request = webServiceRequestFixtures.getSimplePomSha1();
        MavenMetadata metadata = fixtures.getSimplePomSha1();

        when(mavenMetadataBuilder.build()).thenReturn(metadata);

        processor.push(request);

        verify(mavenService).storeHash(metadata, request.getFile());
    }

    @Test
    public void pushPomMd5Hash() {

        DormWebServiceRequest request = webServiceRequestFixtures.getSimplePomMd5();
        MavenMetadata metadata = fixtures.getSimplePomMd5();

        when(mavenMetadataBuilder.build()).thenReturn(metadata);

        processor.push(request);

        verify(mavenService).storeHash(metadata, request.getFile());
    }

    @Test
    public void pushMavenMetadataFile() {

    }

//    @Test
//    public void pushExistingInDormMavenArtifact() {
//
//        DormServiceGetMetadataResult getResult = new DormServiceGetMetadataResult();
//        getResult.addMetadata(metadata);
//
//        Map<String, String> where = new HashMap<String, String>();
//        where.put("url", url);
//
//        List<DormMetadata> metadatas = new ArrayList<DormMetadata>();
//        metadatas.add(metadata);
//
//        when(dao.getMetadataByExtension("maven", where, null)).thenReturn(metadatas);
//
//        DormWebServiceResult result = processor.push(request);
//
//
//    }
//
//    @Test
//    @Ignore
//    public void pushNewMavenArtifact() {
//
//        // empty result
//        DormServiceGetMetadataResult getResult = new DormServiceGetMetadataResult();
//
//        when(service.getMetadata(getValues)).thenReturn(getResult);
//
//        DormWebServiceResult result = processor.push(request);
//    }

//    @Test
//    public void pushStandardMavenArtifact() {
//
//        DependencyNode entityNode = fixtures.getEntityNodeWithChild();
//        DependencyNode childNode = entityNode.getChildren().iterator().next();
//        DormRequest request = fixtures.getRequestWithFile();
//
//        LOG.trace("Test entity node = " + entityNode.getDependency());
//        LOG.trace("Test real dependency node = " + childNode.getDependency());
//        LOG.trace("Test request = " + request);
//
//        DependencyNode pushedNode = processor.push(request);
//
//        Assertions.assertThat(pushedNode).as("Entity node").isEqualTo(entityNode);
//        Assertions.assertThat(pushedNode.getChildren()).as("Real dependency node").
//                isEqualTo(entityNode.getChildren());
//    }
//
//
//    @Test(expected = MavenException.class)
//    public void pushMaventArtifactWithoutFile() {
//        processor.push(fixtures.getRequestWithoutFile());
//    }
//
//    @Test(expected = MavenException.class)
//    public void pushDormArtifactWithoutRequiredMetadatas() {
//
//        DormRequest request = new DormRequestBuilder(fixtures.getRequestWithFile())
//                .property(MavenMetadataExtension.METADATA_ARTIFACTID, null)
//                .build();
//
//        processor.push(request);
//    }

//    @Test
//    public void getMavenMetadataFromRequest() {
//        DormMetadata metadata = processor.getMetadata(fixtures.getRequestWithFilename());
//        Assertions.assertThat(metadata).isEqualTo(fixtures.getMetadata());
//    }

}
