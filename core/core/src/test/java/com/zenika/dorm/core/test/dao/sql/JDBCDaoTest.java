package com.zenika.dorm.core.test.dao.sql;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.dao.sql.DormDaoJdbc;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.test.model.DormMetadataTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
//@Ignore
public class JDBCDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(JDBCDaoTest.class);

    public DormDao dao;


    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new JdbcTestModule());
        dao = injector.getInstance(DormDaoJdbc.class);
    }

    @Test
    public void singlePushTest() {

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
}
