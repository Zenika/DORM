package com.zenika.dorm.core.exception;

@SuppressWarnings("serial")
public class CoreException extends RuntimeException {

    public static enum Type {
        FORBIDDEN, NULL, ERROR
    };

    protected Type type;

    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CoreException type(Type type) {
        this.type = type;
        return this;
    }

    public Type getType() {
        return type;
    }
}
