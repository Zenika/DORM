package com.zenika.dorm.core.dao.sql;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class DormDaoJdbc implements DormDao {

    private static final TypeLiteral<Map<String, String>> typeMap = new TypeLiteral<Map<String, String>>(){};


    @Inject
    private DataSource dataSource;
    @Inject
    private ExtensionFactoryServiceLoader serviceLoader;

    @Override
    public void saveMetadata(final DormMetadata metadata) {
        JDBCSinglePushService jdbcSinglePushService = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(DormMetadata.class).toInstance(metadata);
                        bind(DataSource.class).toInstance(dataSource);
                        bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                    }
                }).getInstance(JDBCSinglePushService.class);
        jdbcSinglePushService.execute();
    }

    @Override
    public DormMetadata getMetadataByQualifier(final String qualifier) {
        JDBCRetrieveByQualifierService jdbcRetrieveByQualifierService = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(String.class).toInstance(qualifier);
                        bind(DataSource.class).toInstance(dataSource);
                        bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                    }
                }).getInstance(JDBCRetrieveByQualifierService.class);
        return jdbcRetrieveByQualifierService.execute();
    }

    @Override
    public List<DormMetadata> getMetadataByExtension(final String extensionName, final Map<String, String> extensionClauses, Usage usage) {
        JDBCRetrieveByExtensionClauseService jdbcRetrieveByExtensionClauseService = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(typeMap).toInstance(extensionClauses);
                        bind(String.class).toInstance(extensionName);
                        bind(DataSource.class).toInstance(dataSource);
                        bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                    }
                }).getInstance(JDBCRetrieveByExtensionClauseService.class);
        return jdbcRetrieveByExtensionClauseService.execute();
    }
}