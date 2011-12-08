package com.zenika.dorm.core.dao.sql;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataLabel;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.slf4j.Logger;

import javax.management.Query;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DormDaoJdbc implements DormDao {

    @Inject
    private DataSource dataSource;

    @Inject
    ExtensionFactoryServiceLoader serviceLoader;

    @Override
    public void saveOrUpdateMetadata(final DormMetadata metadata) {

        DormDaoJdbcQuery query = new DormDaoJdbcQuery("SELECT id FROM dorm_metadata WHERE metadata_name = ? AND extension_name = ? AND metadata_version = ?");
        query.addParam(1, metadata.getName());
        query.addParam(2, metadata.getType());
        query.addParam(3, metadata.getVersion());

        ResultSet res = query.getResultSet();
        Long metadataId = null;
        try {
            if (res.next()) {
                metadataId = res.getLong("id");
            }
        } catch (SQLException e) {
            throw new CoreException("SQL exception", e);
        }

        if (null != metadataId) {
            return;
        }

        query = new DormDaoJdbcQuery("INSERT INTO dorm_metadata (metadata_name, extension_name, metadata_version) VALUES (?, ?, ?);");
        query.addParam(1, metadata.getName());
        query.addParam(2, metadata.getType());
        query.addParam(3, metadata.getVersion());

        metadataId = query.insert();

        Map<String, String> properties = serviceLoader.getInstanceOf(metadata.getType()).toMap(metadata);

        query = new DormDaoJdbcQuery("INSERT INTO dorm_properties (property_key, property_value, metadata_id) VALUES (?, ?, ?)");

        for (Map.Entry<String, String> property : properties.entrySet()) {
            query.addParam(1, property.getKey());
            query.addParam(2, property.getValue());
            query.addParam(3, metadataId);
        }

        query.insert();
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