package com.zenika.dorm.core.dao.sql;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import javax.sql.DataSource;
import java.util.Map;

public class DormDaoJdbc implements DormDao {

    private static final TypeLiteral<Map<String, String>> typeMap = new TypeLiteral<Map<String, String>>() {
    };

    @Inject
    private DataSource dataSource;
    @Inject
    private ExtensionFactoryServiceLoader serviceLoader;

    @Override
    public DormMetadata saveOrUpdateMetadata(final DormMetadata metadata) {
        Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(DormMetadata.class).toInstance(metadata);
                        bind(DataSource.class).toInstance(dataSource);
                        bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                    }
                }).getInstance(JDBCSinglePushTask.class).execute();
    }

    @Override
    public DependencyNode addDependenciesToNode(DependencyNode root) {
        return root;
    }

    @Override
    public DormMetadata get(final DormBasicQuery query) {
        JDBCGetTask jdbcGetTask = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(DormBasicQuery.class).toInstance(query);
                        bind(DataSource.class).toInstance(dataSource);
                        bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                    }
                }).getInstance(JDBCGetTask.class);
        return jdbcGetTask.execute();
    }

}