package com.zenika.dorm.core.dao.sql;

import com.zenika.dorm.core.exception.DaoSQLException;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class JdbcRequestExecutor {

    private static final String ID_COLUMN = "id";
    private static final String ID_CHILD_COLUMN = "id_child";
    private static final String ID_PARENT_COLUMN = "id_parent";
    private static final String USAGE_COLUMN = "usage";
    private static final String PROPERTY_KEY_COLUMN = "property_key";
    private static final String PROPERTY_VALUE_COLUMN = "property_value";
    private static final String EXTENSION_QUALIFIER_COLUMN = "extension_qualifier";
    private static final String EXTENSION_NAME_COLUMN = "extension_name";
    private static final String METADATA_QUALIFIER_COLUMN = "metadata_qualifier";
    private static final String METADATA_VERSION_COLUMN = "metadata_version";
    private static final String METADATA_TYPE_COLUMN = "metadata_type";

    private static final Logger LOG = LoggerFactory.getLogger(DormDaoJdbc.class);
    private static final String DB_URL = "jdbc:postgresql://localhost:5555/DORM_DATA";
    private static final String DB_USER = "dorm";
    private static final String DB_PASSWORD = "dormadmin";

    static {
        try {
            Class.forName(Driver.class.getName());
        } catch (ClassNotFoundException e) {
            LOG.error("Unable to find the Postgres JDBC driver", e);
            throw new DaoSQLException("Unable to find the Postgres JDBC driver", e);
        }
    }

    private Connection connection;

    public JdbcRequestExecutor() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            this.connection.setAutoCommit(false);
            LOG.trace("Thread sleeping...");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            LOG.trace("Thread awake");
        } catch (SQLException e) {
            throw new DaoSQLException("Unable to connect to Postgres server", e);
        }
    }

    public DependencyNode selectDependencyNode(DormMetadata metadata, Usage usage, Map<String, String> properties) {
        PreparedStatement statement = null;
        DormMetadataExtension extension = metadata.getExtension();
        DependencyNode node = DefaultDependencyNode.create(DefaultDependency.create(metadata));
        Map<String, String> extensionProperties = new HashMap<String, String>();
        String metadataQualifier = "";
        String metadataVersion = "";
        String metadataType = "";
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
                        node.addChild(createDependencyNode(usage, extension, extensionProperties, metadataVersion, metadataType));
                        extensionProperties = new HashMap<String, String>();
                    }
                    metadataQualifier = resultSet.getString(METADATA_QUALIFIER_COLUMN);
                    metadataVersion = resultSet.getString(METADATA_VERSION_COLUMN);
                    metadataType = resultSet.getString(METADATA_TYPE_COLUMN);
                    extensionProperties.put("qualifier", resultSet.getString(EXTENSION_QUALIFIER_COLUMN));
                    extensionProperties.put("name", resultSet.getString(EXTENSION_NAME_COLUMN));
                }
                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
                if (resultSet.isLast()) {
                    node.addChild(createDependencyNode(usage, extension, extensionProperties, metadataVersion, metadataType));
                }
            }
            return node;
        } catch (SQLException e) {
            throw new DaoSQLException("Unable to execute request", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoSQLException("Unable to close the statement", e);
                }
            }
        }
    }

    public Dependency selectDependency(DormMetadata metadata) {
        PreparedStatement statement = null;
        try {
            DormMetadataExtension extension = metadata.getExtension();
            String version = null;
            String type = null;
            Map<String, String> extensionProperties = new HashMap<String, String>();
            statement = connection.prepareStatement("SELECT e.property_key, e.property_value, e.extension_qualifier, e.extension_name, m.metadata_version, m.metadata_type \n" +
                    "FROM dorm_metadata m JOIN dorm_extension e ON e.metadata_id = m.id\n" +
                    "WHERE m.metadata_qualifier = ?");
            statement.setString(1, metadata.getQualifier());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
                if (resultSet.isFirst()) {
                    extensionProperties.put("name", resultSet.getString(EXTENSION_NAME_COLUMN));
                    extensionProperties.put("qualifier", resultSet.getString(EXTENSION_QUALIFIER_COLUMN));
                    version = resultSet.getString(METADATA_VERSION_COLUMN);
                    type = resultSet.getString(METADATA_TYPE_COLUMN);
                }
            }
            if (type == null) {
                throw new DaoSQLException("Cannot find the dependency with this Qualifier : " + metadata.getQualifier(), new NullPointerException());
            }
            extension = extension.createFromMap(extensionProperties);
            metadata = DefaultDormMetadata.create(version, type, extension);
            return DefaultDependency.create(metadata);
        } catch (SQLException e) {
            throw new DaoSQLException("Unable to execute request", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoSQLException("Unable to close the statement", e);
                }
            }
        }
    }

    public Long getDependencyId(DormMetadata metadata) {
        PreparedStatement statement = null;
        try {
            Long id = null;
            statement = connection.prepareStatement("SELECT id FROM dorm_metadata WHERE metadata_qualifier = ?");
            statement.setString(1, metadata.getQualifier());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                id = result.getLong(ID_COLUMN);
            }
            return id;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoSQLException("Unable to rollback", e1);
            }
            throw new DaoSQLException("Unable to execute request", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoSQLException("Unable to close the statement", e);
                }
            }
        }
    }

    public Long insertMetadata(DormMetadata metadata) {
        PreparedStatement statement = null;
        try {
            Long id = null;
            statement = connection.prepareStatement("INSERT INTO dorm_metadata (metadata_qualifier, metadata_version, metadata_type) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, metadata.getQualifier());
            statement.setString(2, metadata.getVersion());
            statement.setString(3, metadata.getType());
            if (statement.executeUpdate() > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getLong(1);
                }
            }
            LOG.debug("Id of the new Metadata : " + id);
            return id;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoSQLException("Unable to rollback", e1);
            }
            throw new DaoSQLException("Unable to execute the request", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoSQLException("Unable to close the statement", e);
                }
            }
        }
    }

    public void insertExtension(DormMetadataExtension extension, Long idMetadata) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO dorm_extension (property_key, property_value, extension_qualifier, extension_name, metadata_id) VALUES (?, ?, ?, ?, ?)");
            for (Map.Entry<String, String> properties : MetadataExtensionMapper.fromExtension(extension).entrySet()) {
                statement.setString(1, properties.getKey());
                statement.setString(2, properties.getValue());
                statement.setString(3, extension.getQualifier());
                statement.setString(4, extension.getExtensionName());
                statement.setLong(5, idMetadata);
                statement.execute();
                statement.clearParameters();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoSQLException("Unable to rollback", e1);
            }
            throw new DaoSQLException("Unable to execute the request", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoSQLException("Unable to close the statement", e);
                }
            }
        }
    }

    public void insertDependencyNode(Long idParent, List<Long> idChildren, Usage usage) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM dorm_dependencies WHERE id_parent = ? AND id_child = ?",
                    Statement.RETURN_GENERATED_KEYS,
                    ResultSet.CONCUR_UPDATABLE);
            for (Long idChild : idChildren) {
                statement.setLong(1, idParent);
                statement.setLong(2, idChild);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    resultSet.moveToInsertRow();
                    resultSet.updateLong(ID_PARENT_COLUMN, idParent);
                    resultSet.updateLong(ID_CHILD_COLUMN, idChild);
                    resultSet.updateString(USAGE_COLUMN, usage.getName());
                    resultSet.insertRow();
                }
                statement.clearParameters();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoSQLException("Unable to rollback", e1);
            }
            LOG.trace("Code error : " + e.getErrorCode());
            throw new DaoSQLException("Unable to execute the request : ", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DaoSQLException("Unable to close the statement", e);
                }
            }
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoSQLException("Unable to rollback request", e1);
            }
            throw new DaoSQLException("Unable to commit the transaction", e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoSQLException("Unable to close the connection", e);
        }
    }

    public String generateSelectQuery(int size) {
        StringBuilder str = new StringBuilder(50);
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
//        str.append(")");
        return str.toString();
    }

    private int keyParamIndex(int i) {
        return i * 2 + 1;
    }

    private int valueParamIndex(int i) {
        return i * 2 + 2;
    }

    private DependencyNode createDependencyNode(Usage usage, DormMetadataExtension extension, Map<String, String> extensionProperties, String metadataVersion, String metadataType) {
        DormMetadataExtension extensionTmp = extension.createFromMap(extensionProperties);
        DormMetadata metadataTmp = DefaultDormMetadata.create(metadataVersion, metadataType, extensionTmp);
        Dependency dependency = DefaultDependency.create(metadataTmp, usage);
        return DefaultDependencyNode.create(dependency);
    }
}