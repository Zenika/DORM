package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoJdbcQuery {

    @Inject
    private DataSource dataSource;

    private Connection connection;

    private PreparedStatement statement;

    private ResultSet resultSet;

    private String query;

    private Map<Integer, Object> params = new HashMap<Integer, Object>();

    public DormDaoJdbcQuery(String query) {
        this.query = query;
    }

//    public Object processs(DormDaoJdbcQueryProcess process) {
//
//        Connection connection = null;
//
//        try {
//            connection = dataSource.getConnection();
//            return process.process(getStatement(connection));
//
//        } catch (SQLException e) {
//            throw new CoreException("Unable to get connection from data source", e);
//        } finally {
//            try {
//                DbUtils.close(connection);
//            } catch (SQLException e) {
//                throw new CoreException("Unable to execute request", e);
//            }
//        }
//    }

    public ResultSet getResultSet() {

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            return getStatement(connection).getResultSet();

        } catch (SQLException e) {
            throw new CoreException("Unable to get connection from data source", e);
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                throw new CoreException("Unable to execute request", e);
            }
        }
    }

    public int update() {

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            return getStatement(connection).executeUpdate();

        } catch (SQLException e) {
            throw new CoreException("Unable to get connection from data source", e);
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                throw new CoreException("Unable to execute request", e);
            }
        }
    }

    public long insert() throws CoreException {

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = getStatement(connection);
            connection.commit();

            if (statement.executeUpdate() == 0) {
                throw new CoreException("Unable to save");
            }

            ResultSet res = statement.getGeneratedKeys();
            res.next();

            return res.getLong("id");

        } catch (SQLException e) {
            throw new CoreException("Unable to get connection from data source", e);
        } finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                throw new CoreException("Unable to execute request", e);
            }
        }
    }

    public DormDaoJdbcQuery addOrReplaceParam(int index, Object param) {
        params.put(index, param);
        return this;
    }

    public DormDaoJdbcQuery resetParam() {
        params.clear();
        return this;
    }

    public DormDaoJdbcQuery setQuery(String query) {
        this.query = query;
        return this;
    }

    public void close() {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                
            }
        }
    }

    private PreparedStatement getStatement(Connection connection, boolean generatedKeys) throws SQLException {

        PreparedStatement statement;

        if (generatedKeys) {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } else {
            statement = connection.prepareStatement(query);
        }

        for (Map.Entry<Integer, Object> entry : params.entrySet()) {
            statement.setObject(entry.getKey(), entry.getValue());
        }

        return statement;
    }

    private PreparedStatement getStatement(Connection connection) throws SQLException {
        return getStatement(connection, false);
    }
}
