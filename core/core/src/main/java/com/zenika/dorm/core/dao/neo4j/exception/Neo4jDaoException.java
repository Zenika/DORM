package com.zenika.dorm.core.dao.neo4j.exception;

import com.zenika.dorm.core.exception.CoreException;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jDaoException extends CoreException {

    public Neo4jDaoException() {
        super();
    }

    public Neo4jDaoException(String message) {
        super(message);
    }

    public Neo4jDaoException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
