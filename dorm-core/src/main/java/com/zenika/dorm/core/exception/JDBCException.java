package com.zenika.dorm.core.exception;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class JDBCException extends CoreException {

    public JDBCException() {
    }

    public JDBCException(String message) {
        super(message);
    }

    public JDBCException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
