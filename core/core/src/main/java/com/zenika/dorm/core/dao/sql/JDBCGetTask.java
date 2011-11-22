package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.sun.org.apache.regexp.internal.RE;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JDBCGetTask extends JDBCAbstractTask {

//    public DormMetadata getByQualifier(String name, String version, String extensionName) {
//
//        Connection connection = null;
//
//        try {
//            connection = dataSource.getConnection();
//
//            PreparedStatement statement = connection.prepareStatement("SELECT e.property_key, e.property_value, m.id " +
//                    "FROM dorm_metadata m JOIN dorm_properties e ON e.metadata_id = m.id " +
//                    "WHERE m.metadata_name = ? AND m.extension_name = ? AND m.metadata_version = ?");
//
//            statement.setString(1, name);
//            statement.setString(2, extensionName);
//            statement.setString(3, version);
//
//            ResultSet resultSet = statement.executeQuery();
//
//            return getMetadataFromResultSet(resultSet, extensionName);
//
//        } catch (SQLException e) {
//            throw new CoreException("Unable to execute request", e);
//
//        } finally {
//            if (connection != null) {
//                try {
//                    DbUtils.close(connection);
//                } catch (SQLException e) {
//                    throw new CoreException("Unable to execute request", e);
//                }
//            }
//        }
//    }
//
//    public DormMetadata getById(long id, String extensionName) {
//
//        Connection connection = null;
//
//        try {
//            connection = dataSource.getConnection();
//
//            PreparedStatement statement = connection.prepareStatement("SELECT e.property_key, e.property_value, m.id " +
//                    "FROM dorm_metadata m JOIN dorm_properties e ON e.metadata_id = m.id " +
//                    "WHERE m.id = ? AND m.extension_name = ?");
//
//            statement.setLong(1, id);
//            statement.setString(2, extensionName);
//
//            ResultSet resultSet = statement.executeQuery();
//
//            return getMetadataFromResultSet(resultSet, extensionName);
//
//        } catch (SQLException e) {
//            throw new CoreException("Unable to execute request", e);
//
//        } finally {
//            if (connection != null) {
//                try {
//                    DbUtils.close(connection);
//                } catch (SQLException e) {
//                    throw new CoreException("Unable to execute request", e);
//                }
//            }
//        }
//    }



    /**
     * @return
     * @deprecated
     */
    @Override
    public DormMetadata execute() {
        return null;
    }
}
