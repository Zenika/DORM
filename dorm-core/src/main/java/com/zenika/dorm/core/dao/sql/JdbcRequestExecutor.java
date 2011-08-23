package com.zenika.dorm.core.dao.sql;

import com.zenika.dorm.core.exception.DaoSQLException;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class JdbcRequestExecutor {

    private static final String ID_COLUMN = "id";
    private static final String ID_CHILD_COLUMN = "id_child";
    private static final String ID_PARENT_COLUMN = "id_parent";
    private static final String USAGE_COLUMN = "dorm_usage";
    private static final String DORM_KEY_COLUMN = "dorm_key";
    private static final String DORM_VALUE_COLUMN = "dorm_value";
    private static final String DORM_EXTENSION_QUALIFIER_COLUMN = "dorm_extension_qualifier";
    private static final String DORM_NAME_COLUMN = "dorm_name";
    private static final String DORM_QUALIFIER_COLUMN = "dorm_qualifier";
    private static final String DORM_VERSION_COLUMN = "dorm_version";
    private static final String DORM_TYPE_COLUMN = "dorm_type";

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
        } catch (SQLException e) {
            throw new DaoSQLException("Unable to connect to Postgres server", e);
        }
    }

    public Dependency selectDependency(DormMetadata metadata) {
        PreparedStatement statement = null;
        try {
            DormMetadataExtension extension = metadata.getExtension();
            String version = null;
            String type = null;
            Map<String, String> extensionProperties = new HashMap<String, String>();
            statement = connection.prepareStatement("SELECT e.dorm_key, e.dorm_value, e.dorm_extension_qualifier, e.dorm_name, m.dorm_version, m.dorm_type \n" +
                    "FROM dorm_metadata m JOIN dorm_extension e ON e.dorm_metadata_fk = m.id\n" +
                    "WHERE m.dorm_qualifier = ?");
            statement.setString(1, metadata.getQualifier());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                extensionProperties.put(resultSet.getString(DORM_KEY_COLUMN), resultSet.getString(DORM_VALUE_COLUMN));
                if (resultSet.isFirst()){
                    extensionProperties.put("name", resultSet.getString(DORM_EXTENSION_QUALIFIER_COLUMN));
                    extensionProperties.put("qualifier", resultSet.getString(DORM_NAME_COLUMN));
                    version = resultSet.getString(DORM_VERSION_COLUMN);
                    type = resultSet.getString(DORM_TYPE_COLUMN);
                }
            }
            if (type == null){
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
        LOG.debug("Get metadata : " + metadata.getQualifier());
        PreparedStatement statement = null;
        try {
            Long id = null;
            statement = connection.prepareStatement("SELECT id FROM dorm_metadata WHERE dorm_qualifier = ?");
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
            statement = connection.prepareStatement("INSERT INTO dorm_metadata (dorm_qualifier, dorm_version, dorm_type) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
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
            statement = connection.prepareStatement("INSERT INTO dorm_extension (dorm_key, dorm_value, dorm_extension_qualifier, dorm_name, dorm_metadata_fk) VALUES (?, ?, ?, ?, ?)");
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
                throw new DaoSQLException("Unable to rollback request", e);
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
}
