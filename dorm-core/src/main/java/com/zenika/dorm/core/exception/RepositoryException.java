package com.zenika.dorm.core.exception;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class RepositoryException extends CoreException {

    public RepositoryException(String s) {
        super(s);
    }

    public RepositoryException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
