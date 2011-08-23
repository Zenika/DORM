package com.zenika.dorm.maven.exception;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFormatterException extends MavenException {

    public MavenFormatterException() {
        super();
    }

    public MavenFormatterException(String message) {
        super(message);
    }

    public MavenFormatterException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
