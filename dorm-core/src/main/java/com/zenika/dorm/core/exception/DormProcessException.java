package com.zenika.dorm.core.exception;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormProcessException extends RuntimeException {

    public static enum Type {
        FORBIDDEN, NULL, ERROR
    }

    protected Type type;

    public DormProcessException(String message) {
        super(message);
    }

    public DormProcessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DormProcessException type(Type type) {
        this.type = type;
        return this;
    }

    public Type getType() {
        return type;
    }
}
