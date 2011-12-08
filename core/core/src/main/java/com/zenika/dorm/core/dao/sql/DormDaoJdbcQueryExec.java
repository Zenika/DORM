package com.zenika.dorm.core.dao.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormDaoJdbcQueryExec {

    Object execute(PreparedStatement statement) throws SQLException;
}
