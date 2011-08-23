package com.zenika.dorm.core.exception;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DaoSQLException extends CoreException{

    public DaoSQLException(String message, Throwable throwable){
        super(message, throwable);
    }
}
