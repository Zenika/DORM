package com.zenika.dorm.core.test.dao.nuxeo;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zenika.dorm.core.dao.nuxeo.DormDaoNuxeo;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.test.model.DormMetadataTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(NuxeoDaoTest.class);

    private DormDaoNuxeo dao;

    @Before
    public void setUp(){
        Injector injector = Guice.createInjector(new NuxeoTestModule());
        dao = injector.getInstance(DormDaoNuxeo.class);
    }

    @Test
    public void singlePushTest(){
        DormMetadata metadata = new DormMetadataTest("1.0.0", "its working!");
        dao.saveMetadata(metadata);
    }

    @Test
    public void getByQualifierTest(){
        DormMetadata metadata = dao.getMetadataByFunctionalId("maven:Dorm_test-1.0.0:1.0.0");
        LOG.info("Metadata: " + metadata);
    }
}