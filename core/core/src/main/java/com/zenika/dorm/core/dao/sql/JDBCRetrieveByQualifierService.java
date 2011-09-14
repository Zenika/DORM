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

public class JDBCRetrieveByQualifierService extends JDBCAbstractService {


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
            PreparedStatement statement = connection.prepareStatement("SELECT e.property_key, e.property_value, e.extension_name, m.metadata_version, m.metadata_qualifier " +
                    "FROM dorm_metadata m JOIN dorm_extension e ON e.metadata_id = m.id " +
                    "WHERE m.metadata_qualifier = ?");
            statement.setString(1, qualifier);
            ResultSet resultSet = statement.executeQuery();

            Map<String, String> extensionProperties = new HashMap<String, String>();
            String metadataQualifier = null;
            String metadataVersion = null;
            String extensionName = null;

            while (resultSet.next()) {
                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
                if (resultSet.isFirst()) {
                    metadataQualifier = resultSet.getString(METADATA_QUALIFIER_COLUMN);
                    metadataVersion = resultSet.getString(METADATA_VERSION_COLUMN);
                    extensionName = resultSet.getString(EXTENSION_NAME_COLUMN);
                }
            }
            if (metadataQualifier == null) {
                throw new CoreException("Cannot find the dependency with this Qualifier : " + qualifier);
            }
//            DormMetadata extensionTmp = dormMetadataFactory.getInstanceOf(extensionName).createFromMap(extensionProperties);
//            return DefaultDormMetadata.create(metadataVersion, extensionTmp);
            return null;
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
