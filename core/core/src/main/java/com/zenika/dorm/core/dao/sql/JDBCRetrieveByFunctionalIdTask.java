package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JDBCRetrieveByFunctionalIdTask extends JDBCAbstractTask {


    @Inject
    private String qualifier;

    /**
     * todo: fix from refactoring
     *
     * @return
     */
    @Override
    public DormMetadata execute() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT e.property_key, e.property_value, m.id, m.metadata_qualifier, m.extension_name " +
                    "FROM dorm_metadata m JOIN dorm_properties e ON e.metadata_id = m.id " +
                    "WHERE m.metadata_qualifier = ?");
            statement.setString(1, qualifier);
            ResultSet resultSet = statement.executeQuery();

            Map<String, String> extensionProperties = new HashMap<String, String>();
            String metadataFunctionalId = null;
            String extensionName = null;
            Long id = null;

            while (resultSet.next()) {
                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
                if (resultSet.isFirst()) {
                    metadataFunctionalId = resultSet.getString(METADATA_QUALIFIER_COLUMN);
                    extensionName = resultSet.getString(EXTENSION_NAME_COLUMN);
                    id = resultSet.getLong(ID_COLUMN);
                }
            }
            if (metadataFunctionalId == null) {
                throw new CoreException("Cannot find the dependency with this Qualifier : " + qualifier);
            }
            
            return serviceLoader.getInstanceOf(extensionName).fromMap(id, extensionProperties);
        } catch (SQLException e) {
            throw new CoreException("Unable to execute request", e);
        } finally {
            if (connection != null) {
                try {
                    DbUtils.close(connection);
                } catch (SQLException e) {
                    throw new CoreException("Unable to execute request", e);
                }
            }
        }
    }

}
