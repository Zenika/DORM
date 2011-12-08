package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoJdbcQuery {

    @Inject
    protected DataSource dataSource;

    private String query;

    private Map<Integer, Object> params = new HashMap<Integer, Object>();

    public DormDaoJdbcQuery(String query) {
        this.query = query;
    }

    public int execute() {

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                statement.setObject(entry.getKey(), entry.getValue());
            }

            return statement.executeUpdate();

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

    public ResultSet getResultSet() {

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                statement.setObject(entry.getKey(), entry.getValue());
            }

            return statement.getResultSet();

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

    public DormDaoJdbcQuery addParam(int index, Object param) {
        params.put(index, param);
        return this;
    }
}
