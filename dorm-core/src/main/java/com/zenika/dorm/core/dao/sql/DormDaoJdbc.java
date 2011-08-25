package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.exception.JDBCException;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.get.DormServiceGetValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormDaoJdbc implements DormDao {

    private static final Logger LOG = LoggerFactory.getLogger(DormDaoJdbc.class);

    private static final String DB_URL = "jdbc:postgresql://localhost:5555/DORM_DATA";
    private static final String DB_USER = "dorm";
    private static final String DB_PASSWORD = "dormadmin";

    @Inject
    private DataSource dataSource;

//    public DormDaoJdbc() {
//        executor = new JdbcRequestExecutor();
//    }

    @Override
    public Boolean push(Dependency node) {
        JdbcPushExecutor executor = new JdbcPushExecutor(getConnection(), node);
        executor.execute();
        return true;
    }

    @Override
    public Boolean push(DependencyNode node) {
        JdbcPushExecutor executor = new JdbcPushExecutor(getConnection(), node);
        executor.execute();
        return true;
    }

    @Override
    public DependencyNode getSingleByMetadata(DormMetadata metadata, Usage usage) {
        JdbcSelectExecutor executor = new JdbcSelectExecutor(getConnection(), metadata, usage);
        executor.execute();
        return executor.getNode();
    }

    @Override
    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage) {
        return null;
    }

    @Override
    public DependencyNode getByMetadataExtension(DormMetadata metadata, Usage usage, Map<String, String> params) {
        JdbcSelectExecutor executor = new JdbcSelectExecutor(getConnection(), metadata, params, usage);
        executor.execute();
        return executor.getNode();
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new JDBCException("Unable to retrieve a connection", e);
        }
//        try {
//            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//            connection.setAutoCommit(false);
//            return connection;
//        } catch (SQLException e) {
//            throw new JDBCException("Unable to connect to Postgres server", e);
//        }
    }

    @Override
    public List<DependencyNode> get(DormServiceGetValues values, boolean withDependencies) {
        return null;
    }

    @Override
    public DependencyNode getOne(DormServiceGetValues values, boolean withDependencies) {
        return null;
    }
}