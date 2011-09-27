package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.nuxeo.provider.NuxeoWebResourceWrapper;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormDaoNuxeo implements DormDao {

    public static final String DATA_ENTRY_POINT_URI = "http://192.168.0.37:8090/ecr/dorm";

    @Inject
    private NuxeoWebResourceWrapper wrapper;
    @Inject
    private ExtensionFactoryServiceLoader serviceLoader;

    @Override
    public DormMetadata getMetadataByFunctionalId(final String qualifier) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(NuxeoWebResourceWrapper.class).toInstance(wrapper);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(String.class).toInstance(qualifier);
            }
        }).getInstance(NuxeoRetrieveByQualifier.class).execute();
    }

    @Override
    public DormMetadata get(DormBasicQuery query) {
        return null;
    }

    @Override
    public List<DormMetadata> getMetadataByExtension(String extensionName, Map<String, String> extensionClauses, Usage usage) {
        return null;
    }

    @Override
    public void saveMetadata(final DormMetadata metadata) {
        Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(NuxeoWebResourceWrapper.class).toInstance(wrapper);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormMetadata.class).toInstance(metadata);
            }
        }).getInstance(NuxeoSinglePushTask.class).execute();
    }
}
