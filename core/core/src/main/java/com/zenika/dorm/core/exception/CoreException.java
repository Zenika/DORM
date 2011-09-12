package com.zenika.dorm.core.exception;

@SuppressWarnings("serial")
public class CoreException extends RuntimeException {

    public CoreException() {
        super();
    }

    public CoreException(String s) {
        super(s);
    }

    public CoreException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
