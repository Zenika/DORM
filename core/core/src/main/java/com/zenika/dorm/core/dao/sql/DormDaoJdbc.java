package com.zenika.dorm.core.dao.sql;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.guice.module.DormCoreModule;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;

import java.util.List;
import java.util.Map;

public class DormDaoJdbc implements DormDao {

    @Override
    public void saveMetadata(final DormMetadata metadata) {
        JDBCSinglePushService jdbcSinglePushService = Guice.createInjector(
                new DormCoreModule(),
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(DormMetadata.class).toInstance(metadata);
                    }
                }).getInstance(JDBCSinglePushService.class);
        jdbcSinglePushService.execute();
    }

    @Override
    public DormMetadata getMetadataByQualifier(final String qualifier) {
        JDBCRetrieveByQualifierService jdbcRetrieveByQualifierService = Guice.createInjector(
                new DormCoreModule(),
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(String.class).toInstance(qualifier);
                    }
                }).getInstance(JDBCRetrieveByQualifierService.class);
        return jdbcRetrieveByQualifierService.execute();
    }

    @Override
    public List<DormMetadata> getMetadataByExtension(final String extensionName, final Map<String, String> extensionClauses, Usage usage) {
        JDBCRetrieveByExtensionClauseService jdbcRetrieveByExtensionClauseService = Guice.createInjector(
                new DormCoreModule(),
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(String.class).toInstance(extensionName);
                        bind(Map.class).toInstance(extensionClauses);
                    }
                }).getInstance(JDBCRetrieveByExtensionClauseService.class);
        return jdbcRetrieveByExtensionClauseService.execute();
    }

}