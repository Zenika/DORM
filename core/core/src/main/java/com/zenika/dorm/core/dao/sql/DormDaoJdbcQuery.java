package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoJdbcQuery {

    @Inject
    protected DataSource dataSource;

    private Connection connection;

    private PreparedStatement statement;

    public DormDaoJdbcQuery(String query) {

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

        } catch (SQLException e) {
            closeConnection();
            throw new CoreException("Unable to get connection from data source", e);
        }
    }

    public DormDaoJdbcQuery addStringParam(int index, String param) {

        try {
            statement.setString(index, param);
        } catch (SQLException e) {
            throw new CoreException("Unable to execute request", e);
        }

        return this;
    }

    public DormDaoJdbcQuery addLongParam(int index, long param) {

        try {
            statement.setLong(index, param);
        } catch (SQLException e) {
            throw new CoreException("Unable to execute request", e);
        }

        return this;
    }

    private void closeConnection() {
        try {
            DbUtils.close(connection);
        } catch (SQLException e) {
            throw new CoreException("Unable to execute request", e);
        }
    }
}
