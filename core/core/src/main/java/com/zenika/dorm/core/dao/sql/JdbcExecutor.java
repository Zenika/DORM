package com.zenika.dorm.core.dao.sql;

import com.zenika.dorm.core.exception.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public abstract class JdbcExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcExecutor.class);

    public static final String ID_COLUMN = "id";
    public static final String ID_CHILD_COLUMN = "id_child";
    public static final String ID_PARENT_COLUMN = "id_parent";
    public static final String USAGE_COLUMN = "usage";
    public static final String PROPERTY_KEY_COLUMN = "property_key";
    public static final String PROPERTY_VALUE_COLUMN = "property_value";
    public static final String EXTENSION_QUALIFIER_COLUMN = "extension_qualifier";
    public static final String EXTENSION_NAME_COLUMN = "extension_name";
    public static final String METADATA_QUALIFIER_COLUMN = "metadata_qualifier";
    public static final String METADATA_VERSION_COLUMN = "metadata_version";
    public static final String METADATA_TYPE_COLUMN = "metadata_type";

    protected Connection connection;

    public JdbcExecutor(Connection connection) {
        if (null == connection) {
            throw new JDBCException("Connection is null");
        }

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new JDBCException("Cannot disable autocommit");
        }

        this.connection = connection;
    }

    public abstract void execute();

    protected void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    protected void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new JDBCException("Unable to rollback", e1);
            }
            throw new JDBCException("Unable to commit the transaction", e);
        }
    }

    protected void rollbackSQLException(SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException e1) {
            throw new JDBCException("Unable to rollback", e1);
        }
        throw new JDBCException("Unable to execute the request : ", e);
    }

    protected void finallySQLException(Statement statement) {
        finallySQLException(statement, null);
    }

    protected void finallySQLException(Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOG.error("Unable to close the resultSet", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error("Unable to close the statement", e);
            }
        }
    }
}
