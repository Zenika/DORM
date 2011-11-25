package com.zenika.dorm.core.dao.sql;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.Map;

public class DormDaoJdbc implements DormDao {


    @Inject
    private DataSource dataSource;


    @Override
    public void saveOrUpdateMetadata(final DormMetadata metadata) {
        Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(DormMetadata.class).toInstance(metadata);
                        bind(DataSource.class).toInstance(dataSource);
//                        bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                    }
                }).getInstance(JDBCSinglePushTask.class).execute();
    }

    @Override
    public DependencyNode addDependenciesToNode(DependencyNode root) {
        return root;
    }

    @Override
    public DormMetadataLabel createOrUpdateLabel(DormMetadataLabel metadataLabel) {
        return null;
    }

    @Override
    public DormMetadata getById(long artifactId) {

        DormDaoJdbcQuery query = new DormDaoJdbcQuery("SELECT m FROM dorm_metadata m " +
                "WHERE m.id = ?");

        query.addParam(1, artifactId);

        ResultSet resultSet = query.getResultSet();
        return DormDaoJdbcMapper.mapMetadata(resultSet);
    }

    @Override
    public DormMetadata getById(long artifactId, String extensionName) {

        DormDaoJdbcQuery query = new DormDaoJdbcQuery("SELECT m FROM dorm_metadata m " +
                "WHERE m.id = ?");

        query.addParam(1, artifactId);

        ResultSet resultSet = query.getResultSet();
        return DormDaoJdbcMapper.mapMetadata(resultSet);
    }


    @Override
    public DormMetadata get(final DormBasicQuery query) {
        JDBCGetTask jdbcGetTask = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(DormBasicQuery.class).toInstance(query);
                        bind(DataSource.class).toInstance(dataSource);
//                        bind(ExtensionFactoryServiceLoader.class).toInstance(serviceLoader);
                    }
                }).getInstance(JDBCGetTask.class);
        return jdbcGetTask.execute();
    }

    @Override
    public DormMetadataLabel getByLabel(DormMetadataLabel label) {
        return null;
    }

}