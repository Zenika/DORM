package com.zenika.dorm.core.test.dao.neo4j;

import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.dao.neo4j.Neo4jDependency;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadataExtension;
import com.zenika.dorm.core.dao.neo4j.Neo4jNode;
import com.zenika.dorm.core.dao.neo4j.Neo4jRelationship;
import com.zenika.dorm.core.dao.neo4j.Neo4jResponse;
import com.zenika.dorm.core.dao.neo4j.util.ObjectMapperProvider;
import com.zenika.dorm.core.dao.neo4j.util.RequestExecutor;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.Usage;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jDaoTest.class);

    private static final String INDEX_DEPENDENCY_RESPONSE_JSON = "/com/zenika/dorm/core/test/resources/index_dependency_response.json";
    private static final String DEPENDENCY_RESPONSE_JSON = "/com/zenika/dorm/core/test/resources/dependency_response.json";
    private static final String METADATA_RESPONSE_JSON = "/com/zenika/dorm/core/test/resources/metadata_response.json";
    private static final String EXTENSION_RESPONSE_JSON = "/com/zenika/dorm/core/test/resources/extension_response.json";
    private static final String RELATIONSHIP_DEPENDENCY_RESPONSE_JSON = "/com/zenika/dorm/core/test/resources/relationship_dependency_response.json";
    private static final String RELATIONSHIP_METADATA_RESPONSE_JSON = "/com/zenika/dorm/core/test/resources/relationship_metadata_response.json";
    private static final String INDEX_DEPENDENCY_URI = "http://localhost:7474/db/data/index/node/dependency/fullqualifier/xercesImpl:2.4.0:dorm";
    private static final String DEPENDENCY_URI = "http://localhost:7474/db/data/node/3";
    private static final String RELATIONSHIP_DEPENDENCY_URI = "";

    private URL dependencyUrl;
    private URL metadataUrl;
    private URL extensionUrl;
    private URL relationshipDependencyUrl;
    private URL relationshipMetadataUrl;
    private URL indexDependencyUrl;

    private URI indexUri;
    private URI dependencyUri;

    private TypeReference<Neo4jResponse<Neo4jDependency>> dependencyResponseType;
    private TypeReference<Neo4jResponse<Neo4jMetadata>> metadataResponseType;
    private TypeReference<Neo4jResponse<Neo4jMetadataExtension>> extensionResponseType;
    private TypeReference<List<Neo4jRelationship>> listRelationshipType;
    private TypeReference<List<Neo4jResponse<Neo4jDependency>>> listDependencyResponseType;

    private Neo4jResponse<Neo4jDependency> dependencyResponse;
    private Neo4jResponse<Neo4jMetadata> metadataResponse;
    private Neo4jResponse<Neo4jMetadataExtension> extensionResponse;
    private List<Neo4jRelationship> relationshipsDependency;
    private List<Neo4jRelationship> relationshipsMetadata;
    private List<Neo4jResponse<Neo4jDependency>> listDependencyResponse;

    //    private Dependency dependencyResponse;
//    private DependencyNode dependencyNode;
//    private DormMetadata metadataResponse;
//    private DormMetadataExtension extensionResponse;
    private Usage usage;
    private ObjectMapper mapper;

    @Mock
    private RequestExecutor executor;

    @InjectMocks
    private DormDaoNeo4j dao = new DormDaoNeo4j();

    @Before
    public void setUp() {
        mapper = ObjectMapperProvider.createDefaultMapper();
        setUpUrl();
        setUpUri();
        setUpTypeReference();
        MockitoAnnotations.initMocks(this);
        setUpNeo4jResponseObject();
        setUpMethod();
        usage = Usage.create("DEFAULT");
//        extensionResponse = new DefaultDormMetadataExtension("habi-base");
//        metadataResponse = DefaultDormMetadata.create("0.6", extensionResponse);
//        dependencyResponse = DefaultDependency.create(metadataResponse, usage);
//        dependencyNode = DefaultDependencyNode.create(dependencyResponse);

    }
//
//    @Test
//    public void push() throws Exception {
//        dao.push(dependencyResponse);
//    }
//
//    @Test
//    public void pushDependencyNode() {
//        //dao.push(dependencyNode);
//    }
//
//    @Test
//    public void testPushWithRecursiveChildren() {
//        DormMetadataExtension extensionResponse;
//        DormMetadata metadataResponse;
//        Dependency dependencyResponse;
//        DependencyNode dependencyNode;
//        List<DependencyNode> dependencies = new ArrayList<DependencyNode>();
//        for (int i = 0; i < 100; i++) {
//            extensionResponse = new DefaultDormMetadataExtension("maven" + (i));
//            metadataResponse = DefaultDormMetadata.create("1.0.0", extensionResponse);
//            dependencyResponse = DefaultDependency.create(metadataResponse, Usage.create("DEFAULT"));
//            dependencyNode = DefaultDependencyNode.create(dependencyResponse);
//            dependencies.add(dependencyNode);
//            if (i != 0) {
//                dependencies.get(i - 1).addChild(dependencies.get(i));
//            }
//        }
//        long time = System.currentTimeMillis();
//        dao.push(dependencies.get(0));
//        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
//    }
//
//    @Test
//    public void testPushWithManyChildren() {
//        DormMetadataExtension extensionResponse = new DefaultDormMetadataExtension("maven");
//        DormMetadata metadataResponse = DefaultDormMetadata.create("1.0.0", extensionResponse);
//        Dependency dependencyResponse = DefaultDependency.create(metadataResponse, Usage.create("DEFAULT"));
//        DependencyNode dependencyNode = DefaultDependencyNode.create(dependencyResponse);
//        for (int i = 1; i < 100; i++) {
//            DormMetadataExtension extensionBis = new DefaultDormMetadataExtension("maven" + (i * 10));
//            DormMetadata metadataBis = DefaultDormMetadata.create("1.0.0", extensionBis);
//            Dependency dependencyBis = DefaultDependency.create(metadataBis, Usage.create("DEFAULT"));
//            DependencyNode dependencyNodeBis = DefaultDependencyNode.create(dependencyBis);
//            dependencyNode.addChild(dependencyNodeBis);
//        }
//        long time = System.currentTimeMillis();
//        dao.push(dependencyNode);
//        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
//    }
//
//    @Test
//    public void getByMetaData() {
//        long time = System.currentTimeMillis();
//        dao.getByMetadata(metadataResponse, usage);
//        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
//    }
//
//    @Test
//    public void getOtherByMetadata() {
//        DormMetadataExtension extensionResponse = new DefaultDormMetadataExtension("maven");
//        DormMetadata metadataResponse = DefaultDormMetadata.create("1.0.0", extensionResponse);
//        long time = System.currentTimeMillis();
//        dao.getByMetadata(metadataResponse, usage);
//        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
//    }
//
//    @Test
//    public void pushDependencyNodeWithChildren() {
//        DependencyNode habi_base = createDependencyNode("habi-base", "0.6");
//        DependencyNode commons_cli = createDependencyNode("commons-cli", "1.0");
//        DependencyNode xercesImpl = createDependencyNode("xercesImpl", "2.4.0");
//        DependencyNode commons_logging = createDependencyNode("commons-logging", "1.0.4");
//        DependencyNode jdom = createDependencyNode("jdom", "1.1");
//        DependencyNode xalan = createDependencyNode("xalan", "2.7.0");
//        DependencyNode commons_lang = createDependencyNode("commons-lang", "1.0");
//        DependencyNode junit = createDependencyNode("junit", "3.8.1");
//        DependencyNode xml_apis = createDependencyNode("xml-apis", "1.0b2");
//        habi_base.addChild(commons_cli);
//        habi_base.addChild(xercesImpl);
//        habi_base.addChild(commons_logging);
//        habi_base.addChild(jdom);
//        habi_base.addChild(junit);
//        habi_base.addChild(xalan);
//        commons_cli.addChild(commons_logging);
//        commons_cli.addChild(commons_lang);
//        commons_lang.addChild(junit);
//        xalan.addChild(xml_apis);
//        System.out.println(habi_base.getDependency().getMetadata().getFullQualifier());
//        dao.push(habi_base);
//    }
//
//
//    private DependencyNode createDependencyNode(String name, String version) {
//        DormMetadataExtension extensionResponse = new DefaultDormMetadataExtension(name);
//        DormMetadata metadataResponse = DefaultDormMetadata.create(version, extensionResponse);
//        Dependency dependencyResponse = DefaultDependency.create(metadataResponse, usage);
//        DependencyNode dependencyNode = DefaultDependencyNode.create(dependencyResponse);
//        return dependencyNode;
//    }

    @Test
    public void testSearchNode() {
        LOG.trace("START testSearchNode");
        try {
            Neo4jDependency dependencyResult = dao.searchNode(indexUri, listDependencyResponseType.getType());
            verify(executor).get(any(URI.class), eq(listDependencyResponseType.getType()));
            assertThat(listDependencyResponse.get(0)).isSameAs(dependencyResult.getResponse());
            assertEquals("The Responses doesn't same", listDependencyResponse.get(0), dependencyResult.getResponse());
        } catch (URISyntaxException e) {
            LOG.error("Bad URI", e);
        }
        LOG.trace("END testSearchNode");
    }

    @Test
    public void testFillNeo4jDependency() {
        LOG.trace("START testFillNeo4jDependency");
        try {
            Neo4jDependency dependencyResponse = dao.fillNeo4jDependency(this.dependencyResponse.getData());
            assertThat(dependencyResponse.getMetadata()).isSameAs(metadataResponse.getData());
            assertThat(dependencyResponse.getMetadata().getExtension()).isSameAs(extensionResponse.getData());
        } catch (URISyntaxException e) {
            LOG.error("Bad URI", e);
        }
        LOG.trace("END testFillNeo4jDependency");
    }

    @Test
    public void testGetDependency() {
        LOG.trace("START testGetDependency");
        try {
            Dependency dependency = dao.getDependency(dependencyUri, usage);
            assertThat(dependency).isInstanceOf(Neo4jDependency.class);
            assertThat(dependency.getMetadata()).isSameAs(metadataResponse.getData());
            assertThat(dependency.getMetadata().getExtension()).isSameAs(extensionResponse.getData());
            assertThat(dependency.getUsage()).isSameAs(usage);
        } catch (URISyntaxException e) {
            LOG.error("Bad URI", e);
        }
        LOG.trace("END testGetDependency");
    }

    private void setUpUrl() {
        dependencyUrl = getClass().getResource(DEPENDENCY_RESPONSE_JSON);
        metadataUrl = getClass().getResource(METADATA_RESPONSE_JSON);
        extensionUrl = getClass().getResource(EXTENSION_RESPONSE_JSON);
        relationshipDependencyUrl = getClass().getResource(RELATIONSHIP_DEPENDENCY_RESPONSE_JSON);
        relationshipMetadataUrl = getClass().getResource(RELATIONSHIP_METADATA_RESPONSE_JSON);
        indexDependencyUrl = getClass().getResource(INDEX_DEPENDENCY_RESPONSE_JSON);
    }

    private void setUpUri() {
        try {
            indexUri = new URI(INDEX_DEPENDENCY_URI);
            dependencyUri = new URI(DEPENDENCY_URI);
        } catch (URISyntaxException e) {
            LOG.error("Bad URI", e);
        }
    }

    private void setUpTypeReference() {
        dependencyResponseType = new TypeReference<Neo4jResponse<Neo4jDependency>>() {
        };
        metadataResponseType = new TypeReference<Neo4jResponse<Neo4jMetadata>>() {
        };
        extensionResponseType = new TypeReference<Neo4jResponse<Neo4jMetadataExtension>>() {
        };
        listRelationshipType = new TypeReference<List<Neo4jRelationship>>() {
        };
        listDependencyResponseType = new TypeReference<List<Neo4jResponse<Neo4jDependency>>>() {
        };
    }

    private void setUpNeo4jResponseObject() {
        try {
            dependencyResponse = mapper.readValue(dependencyUrl, dependencyResponseType);
            metadataResponse = mapper.readValue(metadataUrl, metadataResponseType);
            extensionResponse = mapper.readValue(extensionUrl, extensionResponseType);
            relationshipsDependency = mapper.readValue(relationshipDependencyUrl, listRelationshipType);
            relationshipsMetadata = mapper.readValue(relationshipMetadataUrl, listRelationshipType);
            listDependencyResponse = mapper.readValue(indexDependencyUrl, listDependencyResponseType);

            dependencyResponse.getData().setResponse(dependencyResponse);
            metadataResponse.getData().setResponse(metadataResponse);
            extensionResponse.getData().setResponse(extensionResponse);
        } catch (IOException e) {
            LOG.error("Jackson mapper error", e);
        }
    }

    private void setUpMethod() {
        when(executor.get(any(URI.class), eq(listDependencyResponseType.getType()))).thenReturn(listDependencyResponse);
        when(executor.<Neo4jDependency>getNode(any(URI.class), eq(dependencyResponseType.getType())))
                .thenReturn(dependencyResponse.getData());
        when(executor.<Neo4jMetadata>getNode(any(URI.class), eq(metadataResponseType.getType())))
                .thenReturn(metadataResponse.getData());
        when(executor.<Neo4jMetadataExtension>getNode(any(URI.class), eq(extensionResponseType.getType())))
                .thenReturn(extensionResponse.getData());
        when(executor.<List<Neo4jRelationship>>get(any(URI.class), eq(listRelationshipType.getType())))
                .thenReturn(relationshipsDependency);
        when(executor.<List<Neo4jRelationship>>get(any(URI.class), eq(listRelationshipType.getType())))
                .thenReturn(relationshipsMetadata);
    }
}