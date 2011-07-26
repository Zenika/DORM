package com.zenika.dorm.core.test.dao.neo4j;

import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jDaoTest {
    //
    private Dependency dependency;
    private DormMetadata metadata;
    private DormMetadataExtension extension;
    private Usage usage;

    //    //    @Mock
////    private Neo4jRequestExecutor executor;
////
////    @InjectMocks
    private DormDaoNeo4j dao = new DormDaoNeo4j();

    //
//    //
    @Before
    public void setUp() {
        usage = Usage.create("DEFAULT");
        extension = new DefaultDormMetadataExtension("dorm-test");
        metadata = DefaultDormMetadata.create("1.0.0", extension);
        dependency = DefaultDependency.create(metadata, usage);
    }

    //
//    //
    @Test
    public void push() throws Exception {
//        dao.newPush(dependency);
    }


}
