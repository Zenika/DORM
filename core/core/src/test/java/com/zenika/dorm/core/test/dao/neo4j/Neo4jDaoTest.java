package com.zenika.dorm.core.test.dao.neo4j;

import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.test.dao.neo4j.util.Neo4jTestModule;
import com.zenika.dorm.core.test.model.DormMetadataTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Neo4jDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jDaoTest.class);

    @Rule
    public final GuiceBerryRule rule = new GuiceBerryRule(Neo4jTestModule.class);

    private DormDaoNeo4j dao;

    @Before
    public void setUp(){
        Injector injector = Guice.createInjector(new Neo4jTestModule());
        dao = injector.getInstance(DormDaoNeo4j.class);
    }

    @Test
    public void singlePushTest(){
        DormMetadata metadata = new DormMetadataTest("1.0.0", "its working!");
        dao.saveMetadata(metadata);
    }

    @Test
    public void getByQualifierTest(){
        DormMetadata metadata = dao.getMetadataByFunctionalId("Dorm_test:Dorm_test-1.0.0:1.0.0");
        LOG.info("Metadata: " + metadata);
    }
}