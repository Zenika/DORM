package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.sun.xml.bind.v2.util.QNameMap;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(JDBCRetrieveByExtensionClauseService.class);

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

            String query = buildSelectQuery(extensionClause.size());

            PreparedStatement statement = connection.prepareStatement(query);

            int indexStatement = 1;
            for (Map.Entry<String, String> entry: extensionClause.entrySet()) {
                statement.setString(indexStatement++, entry.getKey());
                statement.setString(indexStatement++, entry.getValue());
            }
            statement.setString(indexStatement, extensionName);
            LOG.debug("Query: " + query);
            ResultSet resultSet = statement.executeQuery();

            String metadataQualifier = null;
            Map<String, String> extensionProperties = new HashMap<String, String>();

            while (resultSet.next()) {
                if (!resultSet.getString(METADATA_QUALIFIER_COLUMN).equals(metadataQualifier)) {
                    if (!resultSet.isFirst()) {
                        result.add(createFromProperties(extensionProperties));
                        extensionProperties = new HashMap<String, String>();
                    }
                    metadataQualifier = resultSet.getString(METADATA_QUALIFIER_COLUMN);
                }
                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
                if (resultSet.isLast()) {
                    result.add(createFromProperties(extensionProperties));
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

    /**
     * todo: fix from refactoring
     *
     * @param extensionProperties
     */
    private DormMetadata createFromProperties(Map<String, String> extensionProperties) {
        return serviceLoader.getInstanceOf(extensionName).createFromProperties(extensionProperties);
    }

    private String buildSelectQuery(int size) {
        StringBuilder str = new StringBuilder(100)
                .append("SELECT e.property_key, e.property_value, m.extension_name, m.metadata_qualifier ")
                .append("FROM dorm_metadata m ")
                .append("JOIN dorm_properties e ON e.metadata_id = m.id");
        for (int i = 0; i < size; i++) {
            str.append(" JOIN dorm_properties e")
                    .append(i + 1)
                    .append(" ON (m.id = e")
                    .append(i + 1)
                    .append(".metadata_id and e")
                    .append(i + 1)
                    .append(".property_key = ? and e")
                    .append(i + 1)
                    .append(".property_value = ?)");
        }
        str.append(" WHERE m.extension_name = ?");
        return str.toString();
    }
}
