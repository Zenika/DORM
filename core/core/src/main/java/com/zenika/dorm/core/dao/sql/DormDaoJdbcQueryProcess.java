package com.zenika.dorm.core.dao.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormDaoJdbcQueryProcess {

    Object process(PreparedStatement statement) throws SQLException;
}
