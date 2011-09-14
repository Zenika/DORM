package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class JDBCRetrieveByExtensionClauseService extends JDBCAbstractService {

    @Inject
    private String extensionName;

    @Inject
    private Map<String, String> extensionClause;

    @Override
    public List<DormMetadata> execute() {

        List<DormMetadata> result = new ArrayList<DormMetadata>();

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(buildSelectQuery(extensionClause.size()));
            List<Map.Entry<String, String>> listProperties = new ArrayList<Map.Entry<String, String>>(extensionClause.entrySet());
            statement.setString(1, extensionName);
            for (int i = 0; i < listProperties.size(); i++) {
                statement.setString(keyParamIndex(i + 1), listProperties.get(i + 1).getKey());
                statement.setString(valueParamIndex(i + 1), listProperties.get(i + 1).getValue());
            }
            ResultSet resultSet = statement.executeQuery();

            String metadataQualifier = null;
            String metadataVersion = null;
            Map<String, String> extensionProperties = new HashMap<String, String>();

            while (resultSet.next()) {
                if (!resultSet.getString(METADATA_QUALIFIER_COLUMN).equals(metadataQualifier)) {
                    if (!resultSet.isFirst()) {
                        addMetadata(result, metadataVersion, extensionProperties);
                        extensionProperties = new HashMap<String, String>();
                    }
                    metadataQualifier = resultSet.getString(METADATA_QUALIFIER_COLUMN);
                    metadataVersion = resultSet.getString(METADATA_VERSION_COLUMN);
                }
                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
                if (resultSet.isLast()) {
                    addMetadata(result, metadataVersion, extensionProperties);
                }
            }

            return result;
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


    private void addMetadata(List<DormMetadata> metadatas, String metadataVersion, Map<String, String> extensionProperties) {
        DormMetadataExtension extensionTmp = metadataExtensionFactory.getInstanceOf(extensionName).createFromMap(extensionProperties);
        DormMetadata metadata = DefaultDormMetadata.create(metadataVersion, extensionTmp);
        metadatas.add(metadata);
    }

    private int keyParamIndex(int i) {
        return i * 2;
    }

    private int valueParamIndex(int i) {
        return i * 2 + 1;
    }

    private String buildSelectQuery(int size) {
        StringBuilder str = new StringBuilder(100);
        str.append("SELECT e.property_key, e.property_value, e.extension_qualifier, e.extension_name, m.metadata_version, m.metadata_type, m.metadata_qualifier " +
                "FROM dorm_metadata m " +
                "JOIN dorm_extension e ON e.metadata_id = m.id");
        str.append("JOIN dorm_extension e ON e.extension_name = ?");
        for (int i = 0; i < size; i++) {
            str.append(" JOIN dorm_extension e");
            str.append(i + 1);
            str.append(" ON (m.id = e");
            str.append(i + 1);
            str.append(".metadata_id and e");
            str.append(i + 1);
            str.append(".property_key = ? and e");
            str.append(i + 1);
            str.append(".property_value = ?)");
        }
        return str.toString();
    }
}
