package com.zenika.dorm.guice.module;

import com.google.inject.AbstractModule;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.sql.DormDaoJdbc;
import com.zenika.dorm.core.exception.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormCoreJdbcModule extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(DormCoreJdbcModule.class);

    @Override
    protected void configure() {

        if (LOG.isInfoEnabled()) {
            LOG.info("Configure dorm jdbc guice module");
        }

        DataSource dataSource = null;

        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/postgres");
        } catch (NamingException e) {
            throw new JDBCException("Unable to find the datasource", e);
        }

        bind(DataSource.class).toInstance(dataSource);
        bind(DormDao.class).to(DormDaoJdbc.class);
    }
}
