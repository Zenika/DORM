package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.nuxeo.provider.NuxeoWebResourceWrapper;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormDaoNuxeo implements DormDao {

    private static final String SERVER_HOSTNAME = "192.168.0.37";
    private static final String SERVER_PORT = "8090";

    public static final String DATA_ENTRY_POINT_URI = "http://" + SERVER_HOSTNAME + ":" + SERVER_PORT + "/ecr/dorm";


    @Inject
    private NuxeoWebResourceWrapper wrapper;
    @Inject
    private ExtensionFactoryServiceLoader serviceLoader;

    @Override
    public DormMetadata get(final DormBasicQuery query) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(NuxeoWebResourceWrapper.class).toInstance(wrapper);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormBasicQuery.class).toInstance(query);
            }
        }).getInstance(NuxeoGetTask.class).execute();
    }

    @Override
    public DormMetadataLabel getByLabel(DormMetadataLabel label) {
        return null;
    }

    @Override
    public void saveOrUpdateMetadata(final DormMetadata metadata) {
        Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(NuxeoWebResourceWrapper.class).toInstance(wrapper);
                bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                bind(DormMetadata.class).toInstance(metadata);
            }
        }).getInstance(NuxeoSinglePushTask.class).execute();
    }

    @Override
    public DependencyNode addDependenciesToNode(DependencyNode root) {
        return root;
    }
}
