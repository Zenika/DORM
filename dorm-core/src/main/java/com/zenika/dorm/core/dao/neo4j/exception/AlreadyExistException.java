package com.zenika.dorm.core.dao.neo4j.exception;

import com.zenika.dorm.core.exception.CoreException;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class AlreadyExistException extends CoreException{

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String message, Throwable throwable) {
        super(message, throwable);
    }
}