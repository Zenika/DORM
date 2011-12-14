package com.zenika.dorm.core.exception;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class SecurityException extends CoreException {

    public SecurityException() {
    }

    public SecurityException(Throwable throwable) {
        super(throwable);
    }

    public SecurityException(String s) {
        super(s);
    }

    public SecurityException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
