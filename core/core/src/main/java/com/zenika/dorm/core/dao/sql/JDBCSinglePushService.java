package com.zenika.dorm.core.dao.sql;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.*;
import java.util.Map;

public class JDBCSinglePushService extends JDBCAbstractService {

    private static final Logger LOG = LoggerFactory.getLogger(JDBCSinglePushService.class);

    @Inject
    private DormMetadata metadata;

    @Override
    public Void execute() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            Long id = getDependencyId(connection);
            if (id == null) {
                id = insertMetadata(connection);
                insertExtension(connection, id);
            } else {
                LOG.debug("The dependency " + metadata.getQualifier() + " already insert in database");
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new CoreException("Unable to rollback", e);
                }
            }
            throw new CoreException("Unable to execute query: ", e);
        } finally {
            if (connection != null) {
                try {
                    DbUtils.close(connection);
                } catch (SQLException e) {
                    throw new CoreException("Unable to close the connection", e);
                }
            }
        }
        return null;
    }

    private Long getDependencyId(Connection connection) throws SQLException {
        Long id = null;
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM dorm_metadata WHERE metadata_qualifier = ?");
        statement.setString(1, metadata.getQualifier());
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            id = result.getLong(ID_COLUMN);
        }
        return id;
    }

    private Long insertMetadata(Connection connection) throws SQLException {
        Long id = null;
        PreparedStatement statement = connection.prepareStatement("INSERT INTO dorm_metadata (metadata_qualifier, metadata_version) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, metadata.getQualifier());
        statement.setString(2, metadata.getVersion());
        if (statement.executeUpdate() > 0) {
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        }
        LOG.debug("Id of the new Metadata : " + id);
        return id;
    }

    private void insertExtension(Connection connection, Long metadataId) throws SQLException {
        DormMetadataExtension extension = metadata.getExtension();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO dorm_extension (property_key, property_value, extension_qualifier, extension_name, metadata_id) VALUES (?, ?, ?, ?, ?)");
        for (Map.Entry<String, String> properties : MetadataExtensionMapper.fromExtension(extension).entrySet()) {
            statement.setString(1, properties.getKey());
            statement.setString(2, properties.getValue());
            statement.setString(3, extension.getQualifier());
            statement.setString(4, extension.getExtensionName());
            statement.setLong(5, metadataId);
            statement.execute();
            statement.clearParameters();
        }
    }
}
