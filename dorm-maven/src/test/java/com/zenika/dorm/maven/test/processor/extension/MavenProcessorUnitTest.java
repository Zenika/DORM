package com.zenika.dorm.maven.test.processor.extension;


import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.model.ws.builder.DormWebServiceRequestBuilder;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataResult;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataValues;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * Unit tests for the maven processor
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class MavenProcessorUnitTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProcessorUnitTest.class);

    private String path = "org/apache/wicket/wicket/1.4.9";
    private String filename = "wicket-1.4.9.jar";
    private String url = path + "/" + filename;

    private DormWebServiceRequest request = new DormWebServiceRequestBuilder("maven")
            .property("path", path)
            .filename(filename)
            .file(fixtures.getFile())
            .build();

    private MavenMetadataExtension metadataExtension = new MavenMetadataExtension(url);
    private DormMetadata metadata = DefaultDormMetadata.create(null, metadataExtension);
    private DormServiceGetMetadataValues getValues = new DormServiceGetMetadataValues(metadata);

    @Mock
    private DormService service;

    @Mock
    private DormDao dao;

    @InjectMocks
    private MavenProcessor processor = new MavenProcessor();

    @Test
    public void pushExistingInDormMavenArtifact() {

        DormServiceGetMetadataResult getResult = new DormServiceGetMetadataResult();
        getResult.addMetadata(metadata);

        Map<String, String> where = new HashMap<String, String>();
        where.put("url", url);

        List<DormMetadata> metadatas = new ArrayList<DormMetadata>();
        metadatas.add(metadata);

        when(dao.getByMetadataExtension("maven", where, null)).thenReturn(metadatas);

        DormWebServiceResult result = processor.push(request);


    }

    @Test
    public void pushNewMavenArtifact() {

        // empty result
        DormServiceGetMetadataResult getResult = new DormServiceGetMetadataResult();

        when(service.getMetadata(getValues)).thenReturn(getResult);

        DormWebServiceResult result = processor.push(request);
    }

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
