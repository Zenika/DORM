package com.zenika.dorm.core.test.dao.neo4j;

import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.dao.neo4j.Neo4jDependency;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import com.zenika.dorm.core.dao.neo4j.Neo4jResponse;
import com.zenika.dorm.core.dao.neo4j.util.ObjectMapperProvider;
import com.zenika.dorm.core.dao.neo4j.util.RequestExecutor;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    private static final String INDEX_DEPENDENCY_RESPONSE_JSON = "/com/zenika/dorm/core/test/resources/index_dependency_response.json";
    private static final String INDEX_DEPENDENCY_URI = "http://localhost:7474/db/data/index/node/dependency/fullqualifier/xercesImpl:2.4.0:dorm";

    private Dependency dependency;
    private DependencyNode dependencyNode;
    private DormMetadata metadata;
    private DormMetadataExtension extension;
    private Usage usage;
    private ObjectMapper mapper;

    @Mock
    private RequestExecutor executor;

    @InjectMocks
    private DormDaoNeo4j dao = new DormDaoNeo4j();

    @Before
    public void setUp() {
        
        MockitoAnnotations.initMocks(this);
        usage = Usage.create("DEFAULT");
        extension = new DefaultDormMetadataExtension("habi-base");
        metadata = DefaultDormMetadata.create("0.6", extension);
        dependency = DefaultDependency.create(metadata, usage);
        dependencyNode = DefaultDependencyNode.create(dependency);
        mapper = ObjectMapperProvider.createDefaultMapper();
    }
//
//    @Test
//    public void push() throws Exception {
//        dao.push(dependency);
//    }
//
//    @Test
//    public void pushDependencyNode() {
//        //dao.push(dependencyNode);
//    }
//
//    @Test
//    public void testPushWithRecursiveChildren() {
//        DormMetadataExtension extension;
//        DormMetadata metadata;
//        Dependency dependency;
//        DependencyNode dependencyNode;
//        List<DependencyNode> dependencies = new ArrayList<DependencyNode>();
//        for (int i = 0; i < 100; i++) {
//            extension = new DefaultDormMetadataExtension("maven" + (i));
//            metadata = DefaultDormMetadata.create("1.0.0", extension);
//            dependency = DefaultDependency.create(metadata, Usage.create("DEFAULT"));
//            dependencyNode = DefaultDependencyNode.create(dependency);
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
//        DormMetadataExtension extension = new DefaultDormMetadataExtension("maven");
//        DormMetadata metadata = DefaultDormMetadata.create("1.0.0", extension);
//        Dependency dependency = DefaultDependency.create(metadata, Usage.create("DEFAULT"));
//        DependencyNode dependencyNode = DefaultDependencyNode.create(dependency);
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
//        dao.getByMetadata(metadata, usage);
//        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
//    }
//
//    @Test
//    public void getOtherByMetadata() {
//        DormMetadataExtension extension = new DefaultDormMetadataExtension("maven");
//        DormMetadata metadata = DefaultDormMetadata.create("1.0.0", extension);
//        long time = System.currentTimeMillis();
//        dao.getByMetadata(metadata, usage);
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
//        DormMetadataExtension extension = new DefaultDormMetadataExtension(name);
//        DormMetadata metadata = DefaultDormMetadata.create(version, extension);
//        Dependency dependency = DefaultDependency.create(metadata, usage);
//        DependencyNode dependencyNode = DefaultDependencyNode.create(dependency);
//        return dependencyNode;
//    }

    @Test
    public void testSearchNode() throws URISyntaxException {
        URL file = getClass().getResource(INDEX_DEPENDENCY_RESPONSE_JSON);
        URI uri = new URI(INDEX_DEPENDENCY_URI);
        TypeReference<List<Neo4jResponse<Neo4jDependency>>> type = new TypeReference<List<Neo4jResponse<Neo4jDependency>>>() {
        };
        try {
            List<Neo4jResponse<Neo4jDependency>> response = mapper.readValue(file, type);
            when(executor.get(uri, type.getType())).thenReturn(response);
            Neo4jDependency dependencyResult = dao.searchNode(uri, type.getType());
            verify(executor).get(uri,type.getType());
            assertThat(response.get(0)).isSameAs(dependencyResult.getResponse());
            assertEquals("The Responses doesn't same", response.get(0), dependencyResult.getResponse());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}