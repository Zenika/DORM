package com.zenika.dorm.maven.exception;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenResourceException extends MavenException {

    public MavenResourceException() {
        super();
    }

    public MavenResourceException(String message) {
        super(message);
    }

    public MavenResourceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
