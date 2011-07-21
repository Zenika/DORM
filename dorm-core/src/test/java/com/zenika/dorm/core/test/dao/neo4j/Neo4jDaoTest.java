package com.zenika.dorm.core.test.dao.neo4j;

import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

    @Mock
    private Neo4jRequestExecutor executor;

    @InjectMocks
    private DormDaoNeo4j dao = new DormDaoNeo4j();

    @Before
    public void setUp(){
        usage = Usage.create("DEFAULT");
        origin = new DefaultDormMetadataExtension("dorm-test");
        metadata = new DefaultDormMetadata("1.0.0", origin);
        dependency = new DefaultDependency(metadata, usage);
    }

//    @Test
//    public void push() throws Exception {
//        given(executor.postNode("jsonProperties")).willReturn("nodeUri");
////        given(executor.createRelationship())
//        dao.push(dependency);
//
//    }

}
