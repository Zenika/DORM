package com.zenika.dorm.core.dao.sql;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

public class DormDaoJdbc implements DormDao {

    @Inject
    private DataSource dataSource;

    @Inject
    ExtensionFactoryServiceLoader serviceLoader;

    @Override
    public void saveOrUpdateMetadata(final DormMetadata metadata) {
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
        throw new UnsupportedOperationException();
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

        DormDaoJdbcQuery query = new DormDaoJdbcQuery("SELECT e.property_key, e.property_value, m.id " +
                "FROM dorm_metadata m JOIN dorm_properties e ON e.metadata_id = m.id " +
                "WHERE m.id = ?");

        query.addParam(1, artifactId);

        ResultSet resultSet = query.getResultSet();
        return DormDaoJdbcMapper.mapMetadataWithExtension(resultSet, extensionName);
    }

    @Override
    public DormMetadata get(DormBasicQuery basicQuery) {

        DormDaoJdbcQuery query = new DormDaoJdbcQuery("SELECT e.property_key, e.property_value, m.id " +
                "FROM dorm_metadata m JOIN dorm_properties e ON e.metadata_id = m.id " +
                "WHERE m.metadata_name = ? AND m.extension_name = ? AND m.metadata_version = ?");

        query.addParam(1, basicQuery.getName());
        query.addParam(2, basicQuery.getExtensionName());
        query.addParam(3, basicQuery.getVersion());

        ResultSet resultSet = query.getResultSet();
        return DormDaoJdbcMapper.mapMetadataWithExtension(resultSet, basicQuery.getExtensionName());
    }

    @Override
    public DormMetadataLabel getByLabel(DormMetadataLabel label, String extensionName) {

        DormDaoJdbcQuery query = new DormDaoJdbcQuery("SELECT e.property_key, e.property_value, m.id FROM dorm_metadata m " +
                "JOIN dorm_properties e ON e.metadata_id = m.id " +
                "JOIN dorm_metadata_labels dml ON dml.metadata_id = m.id " +
                "JOIN labels l ON dml.label_id = l.id " +
                "WHERE l.name = ? AND m.extension_name = ?");

        query.addParam(1, label.getLabel());
        query.addParam(2, extensionName);

        ResultSet resultSet = query.getResultSet();
        return null;
//        List<> DormDaoJdbcMapper.mapMetadataWithExtension(resultSet, extensionName);
    }
}