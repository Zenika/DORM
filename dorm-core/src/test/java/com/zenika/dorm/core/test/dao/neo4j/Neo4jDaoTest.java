package com.zenika.dorm.core.test.dao.neo4j;

import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.dao.neo4j.Neo4jOrigin;
import com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jDaoTest {

    private Dependency dependency;
    private DormMetadata metadata;
    private DormMetadataExtension origin;
    private Usage usage;

//    @Mock
//    private Neo4jRequestExecutor executor;
//
//    @InjectMocks
    private DormDaoNeo4j dao = new DormDaoNeo4j();
//
    @Before
    public void setUp(){
        usage = Usage.create("DEFAULT");
        origin = new DefaultDormMetadataExtension("dorm-test");
        metadata = new DefaultDormMetadata("1.0.0", origin);
        dependency = new DefaultDependency(metadata, usage);
    }
//
    @Test
    public void push() throws Exception {
//        given(executor.postNode("jsonProperties")).willReturn("nodeUri");
//        given(executor.createRelationship("node", "child", usage));
        dao.newPush(dependency);

//
    }

//    @Test
//    public void test(){
//        DormOrigin origin = new DefaultDormOrigin("dorm");
//        Neo4jOrigin neo4jOrigin = new Neo4jOrigin(origin);
//        ObjectMapper mapper = new ObjectMapper();
//        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
//        mapper.setAnnotationIntrospector(introspector);
//        try {
//            mapper.writeValue(System.out, neo4jOrigin);
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }

}
