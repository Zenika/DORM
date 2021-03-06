package com.zenika.dorm.core.dao.sql;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.*;
import java.util.Map;

public class JDBCSinglePushTask extends JDBCAbstractTask {

    private static final Logger LOG = LoggerFactory.getLogger(JDBCSinglePushTask.class);

    @Inject
    private DormMetadata metadata;

    @Override
    public DormMetadata execute() {
        Connection connection = null;
        DormMetadata metadata = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            Long id = getDependencyId(connection);
            if (id == null) {
                id = insertMetadata(connection);
                insertExtension(connection, id);
            } else {
                LOG.debug("The dependency " + this.metadata.getName() + " already insert in database");
            }
            connection.commit();
            ExtensionMetadataFactory factory = serviceLoader.getInstanceOf(this.metadata.getType());
            Map<String, String> properties = factory.toMap(this.metadata);
            metadata = factory.fromMap(id, properties);
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
        return metadata;
    }

    private Long getDependencyId(Connection connection) throws SQLException {
        Long id = null;
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM dorm_metadata WHERE metadata_name = ? AND extension_name = ? AND metadata_version = ?");
        statement.setString(1, metadata.getName());
        statement.setString(2, metadata.getType());
        statement.setString(3, metadata.getVersion());
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            id = result.getLong(ID_COLUMN);
        }
        return id;
    }

    private Long insertMetadata(Connection connection) throws SQLException {
        Long id = null;
        PreparedStatement statement = connection.prepareStatement("INSERT INTO dorm_metadata (metadata_name, extension_name, metadata_version) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, metadata.getName());
        statement.setString(2, metadata.getType());
        statement.setString(3, metadata.getVersion());
        if (statement.executeUpdate() > 0) {
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        }
        LOG.debug("Id of the new Metadata : " + id);
        return id;
    }

    /**
     * @param connection
     * @param metadataId
     * @throws SQLException
     */
    private void insertExtension(Connection connection, Long metadataId) throws SQLException {
        Map<String, String> properties = serviceLoader
                .getInstanceOf(metadata.getType())
                .toMap(metadata);

        PreparedStatement statement = connection.prepareStatement("INSERT INTO dorm_properties (property_key, property_value, metadata_id) VALUES (?, ?, ?)");
        for (Map.Entry<String, String> property : properties.entrySet()) {
            statement.setString(1, property.getKey());
            statement.setString(2, property.getValue());
            statement.setLong(3, metadataId);
            statement.execute();
            statement.clearParameters();
        }
    }
}
