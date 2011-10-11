package com.zenika.dorm.core.exception;

@SuppressWarnings("serial")
public class CoreException extends RuntimeException {

    private Type type = Type.ERROR;

    public CoreException() {
        super();
    }

    public CoreException(Throwable throwable) {
        super(throwable);
    }

    public CoreException(String s) {
        super(s);
    }

    public CoreException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CoreException type(Type type) {
        this.type = type;
        return this;
    }

    public Type getType() {
        return type;
    }

    public static enum Type {
        NULL, ERROR
    }
}
