package com.zenika.dorm.core.guice.module.dao;

import com.google.inject.AbstractModule;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.sql.DormDaoJdbc;
import com.zenika.dorm.core.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import static com.google.inject.jndi.JndiIntegration.fromJndi;

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

        Context context;
        try {
            context = new InitialContext();
        } catch (NamingException e) {
            throw new CoreException("Unable to initate the JNDI context", e);
        }

        if (null == context) {
            throw new CoreException("JNDI context is null");
        }

        bind(Context.class).toInstance(context);
        bind(DataSource.class).toProvider(fromJndi(DataSource.class, "java:/comp/env/jdbc/postgres"));

        bind(DormDao.class).to(DormDaoJdbc.class);
    }
}
