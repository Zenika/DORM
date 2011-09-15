package com.zenika.dorm.core.dao.sql;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.DormQualifier;
import com.zenika.dorm.core.model.impl.Usage;

import java.util.List;
import java.util.Map;

public class DormDaoJdbc implements DormDao {

    @Inject
    private Injector injector;

    @Override
    public void saveMetadata(DormQualifier qualifier, final DormMetadata metadata) {
        JDBCSinglePushService jdbcSinglePushService = injector.createChildInjector(
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
        JDBCRetrieveByQualifierService jdbcRetrieveByQualifierService = injector.createChildInjector(
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
        JDBCRetrieveByExtensionClauseService jdbcRetrieveByExtensionClauseService = injector.createChildInjector(
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