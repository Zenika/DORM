package com.zenika.dorm.core.dao.sql;

import com.zenika.dorm.core.exception.JDBCException;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetValues;

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
public class JdbcSelectExecutor extends JdbcExecutor {

    private List<DependencyNode> nodes = new ArrayList<DependencyNode>();
    private DependencyNode node;
    private DormMetadataExtension extension;
    private Map<String, String> properties;
    private Usage usage;

    private String metadataQualifier;
    private String metadataVersion;
    private String metadataType;
    private Map<String, String> extensionProperties = new HashMap<String, String>();

    private DormServiceGetValues values;

    public JdbcSelectExecutor(Connection connection, DormServiceGetValues values) {
        super(connection);
        this.values = values;
        this.extension = values.getMetadataExtension();
        this.usage = values.getUsage();
    }

    @Override
    public void execute() {
        if (values.hasMetadataExtensionClauses()) {
            properties = values.getMetadataExtensionClauses();
            selectDependencyNode();
        } else {
            selectDependency();
        }
    }

    public DependencyNode getNode() {
        return node;
    }

    public List<DependencyNode> getNodes(){
        return nodes;
    }

    private void selectDependencyNode() {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(generateSelectQuery(properties.size()));
            List<Map.Entry<String, String>> listProperties = new ArrayList<Map.Entry<String, String>>(properties.entrySet());
            for (int i = 0; i < listProperties.size(); i++) {
                statement.setString(keyParamIndex(i), listProperties.get(i).getKey());
                statement.setString(valueParamIndex(i), listProperties.get(i).getValue());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getString(METADATA_QUALIFIER_COLUMN).equals(metadataQualifier)) {
                    if (!resultSet.isFirst()) {
                        nodes.add(createDependencyNode(extension));
                        extensionProperties = new HashMap<String, String>();
                    }
                    metadataQualifier = resultSet.getString(METADATA_QUALIFIER_COLUMN);
                    metadataVersion = resultSet.getString(METADATA_VERSION_COLUMN);
                    metadataType = resultSet.getString(METADATA_TYPE_COLUMN);
                }
                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
                if (resultSet.isLast()) {
                    nodes.add(createDependencyNode(extension));
                }
            }
            if (metadataQualifier == null) {
                throw new JDBCException("Cannot find the dependency with this extension clause : " + extensionProperties, new NullPointerException());
            }
        } catch (SQLException e) {
            throw new JDBCException("Unable to execute request", e);
        } finally {
            finallySQLException(statement);
        }
    }

    private void selectDependency() {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT e.property_key, e.property_value,m.metadata_version, m.metadata_type, m.metadata_qualifier " +
                    "FROM dorm_metadata m JOIN dorm_extension e ON e.metadata_id = m.id " +
                    "WHERE m.metadata_qualifier = ?");
            statement.setString(1, values.getQualifier());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
                if (resultSet.isFirst()) {
                    metadataQualifier = resultSet.getString(METADATA_QUALIFIER_COLUMN);
                    metadataVersion = resultSet.getString(METADATA_VERSION_COLUMN);
                    metadataType = resultSet.getString(METADATA_TYPE_COLUMN);
                }
            }
            if (metadataQualifier == null) {
                throw new JDBCException("Cannot find the dependency with this Qualifier : " + values.getQualifier(), new NullPointerException());
            }
            node = createDependencyNode(extension.createFromMap(extensionProperties));
        } catch (SQLException e) {
            throw new JDBCException("Unable to execute request", e);
        } finally {
            finallySQLException(statement);
        }
    }

    private String generateSelectQuery(int size) {
        StringBuilder str = new StringBuilder(100);
        str.append("SELECT e.property_key, e.property_value, e.extension_qualifier, e.extension_name, m.metadata_version, m.metadata_type, m.metadata_qualifier " +
                "FROM dorm_metadata m " +
                "JOIN dorm_extension e ON e.metadata_id = m.id");
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

    private int keyParamIndex(int i) {
        return i * 2 + 1;
    }

    private int valueParamIndex(int i) {
        return i * 2 + 2;
    }

    private DependencyNode createDependencyNode(DormMetadataExtension extension) {
        DormMetadataExtension extensionTmp = extension.createFromMap(extensionProperties);
        DormMetadata metadataTmp = DefaultDormMetadata.create(metadataVersion, metadataType, extensionTmp);
        Dependency dependency = DefaultDependency.create(metadataTmp, usage);
        return DefaultDependencyNode.create(dependency);
    }

}