package com.zenika.dorm.maven.exception;

import com.zenika.dorm.core.exception.CoreException;

public class MavenException extends CoreException {

    public MavenException() {
        super();
    }

    public MavenException(String message) {
        super(message);
    }

    public MavenException(String message, Throwable throwable) {
        super(message, throwable);
    }


}
