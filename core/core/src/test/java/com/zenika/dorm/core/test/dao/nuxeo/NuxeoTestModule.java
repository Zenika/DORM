package com.zenika.dorm.core.test.dao.nuxeo;

import com.google.inject.AbstractModule;
import com.zenika.dorm.core.dao.nuxeo.provider.NuxeoWebResourceWrapper;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class NuxeoTestModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(NuxeoWebResourceWrapper.class);
        bind(ExtensionFactoryServiceLoader.class);
    }
}
