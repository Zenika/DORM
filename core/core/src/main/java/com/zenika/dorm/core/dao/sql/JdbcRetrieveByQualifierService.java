package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class JdbcRetrieveByQualifierService extends JdbcAbstractService {

//    private static final Logger LOG = LoggerFactory.getLogger(JdbcSelectAbstractService.class);

    @Inject
    private String qualifier;

    private DormMetadata metadata;

    @Override
    public void execute() {
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
            DormMetadataExtension extensionTmp = metadataExtensionFactory.getInstanceOf(extensionName).createFromMap(extensionProperties);
            metadata = DefaultDormMetadata.create(metadataVersion, extensionTmp);
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

    public DormMetadata getMetadata(){
        return metadata;
    }
}
