package com.zenika.dorm.core.test.dao.neo4j;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.test.dao.neo4j.util.Neo4jTestModule;
import com.zenika.dorm.core.test.model.DormMetadataTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.assertions.Assertions.assertThat;

public class Neo4jDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jDaoTest.class);

    private DormDaoNeo4j dao;
    private Neo4jTestProvider provider;

    @Before
    public void setUp() {
        provider = Neo4jTestProvider.getProvider();
        Injector injector = Guice.createInjector(new Neo4jTestModule());
        dao = injector.getInstance(DormDaoNeo4j.class);
    }

    @Test
    public void testSinglePushTest() {

        DormMetadata expectMetadata = DormMetadataTest.getDefault();
        dao.saveOrUpdateMetadata(expectMetadata);

        DormBasicQuery query = new DormBasicQuery.Builder()
                .extensionName("DormTest")
                .name("DormTest-1.0.0")
                .version("1.0.0")
                .build();
        DormMetadata resultMetadata = dao.get(query);

        LOG.info("Metadata: " + resultMetadata);

        assertThat(resultMetadata).isEqualTo(expectMetadata);
    }

    @Test
    public void testAddDependenciesToNode() {
        for (DependencyNode node:provider.getDependencies()){
            DormMetadata metadata = node.getDependency().getMetadata();
            dao.saveOrUpdateMetadata(metadata);
        }

        DependencyNode root = provider.getRoot();
        dao.addDependenciesToNode(root);
    }

}