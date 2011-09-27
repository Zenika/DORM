package com.zenika.dorm.core.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.dao.nuxeo.DormDaoNuxeo;
import com.zenika.dorm.core.dao.nuxeo.provider.NuxeoWebResourceWrapper;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormCoreNuxeoModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(NuxeoWebResourceWrapper.class);

        bind(DormDao.class).to(DormDaoNuxeo.class);
    }
}
