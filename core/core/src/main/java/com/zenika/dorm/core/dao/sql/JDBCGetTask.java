package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JDBCGetTask extends JDBCAbstractTask {


    @Inject
    private DormBasicQuery query;

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
            PreparedStatement statement = connection.prepareStatement("SELECT e.property_key, e.property_value, m.id " +
                    "FROM dorm_metadata m JOIN dorm_properties e ON e.metadata_id = m.id " +
                    "WHERE m.metadata_name = ? AND m.extension_name = ? AND m.metadata_version = ?");
            statement.setString(1, query.getName());
            statement.setString(2, query.getExtensionName());
            statement.setString(3, query.getVersion());
            ResultSet resultSet = statement.executeQuery();

            Map<String, String> extensionProperties = new HashMap<String, String>();

            Long id = null;

            while (resultSet.next()) {
                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
                if (resultSet.isFirst()) {
                    id = resultSet.getLong(ID_COLUMN);
                }
            }
            if (id == null) {
                throw new CoreException("Cannot find the dependency with this Qualifier : " + query);
            }
            return serviceLoader.getInstanceOf(query.getExtensionName()).fromMap(id, extensionProperties);
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
